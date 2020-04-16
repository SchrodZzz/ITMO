package ru.ifmo.ivshin.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloUDPClient implements HelloClient {

    public static void main(String[] args) {
        if (args == null || args.length != 5 || Arrays.stream(args).anyMatch(Objects::isNull)) {
            System.out.println("Wrong arguments");
            return;
        }
        try {
            new HelloUDPClient().run(args[0], Integer.parseInt(args[1]), args[2],
                    Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        } catch (NumberFormatException e) {
            System.out.println("Numeric arguments parse error: " + e.getMessage());
        }

    }

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        try {
            SocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
            processAll(address, prefix, threads, requests);
        } catch (UnknownHostException e) {
            System.err.println("Unable to reach specified host: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Execution was interrupted: " + e.getMessage());
        }
    }

    private void processAll(SocketAddress address, String prefix, int threads, int requests)
            throws InterruptedException {
        ExecutorService workers = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            final int finalI = i;
            workers.submit(() -> processTask(address, prefix, finalI, requests));
        }
        workers.shutdown();
        workers.awaitTermination(5 * requests * threads, TimeUnit.SECONDS);
    }

    private void processTask(SocketAddress address, String prefix, int threadId, int requests) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(200);
            int bufferSize = socket.getReceiveBufferSize();
            final DatagramPacket request = new DatagramPacket(new byte[bufferSize], bufferSize, address);
            for (int requestId = 0; requestId < requests; requestId++) {
                String requestMessage = prefix + threadId + "_" + requestId;
                System.out.printf("Sending '%s'", requestMessage);
                boolean received = false;
                while (!received && !socket.isClosed() && !Thread.interrupted()) {
                    try {
                        request.setData(requestMessage.getBytes(StandardCharsets.UTF_8));
                        socket.send(request);
                        request.setData(new byte[bufferSize]);
                        socket.receive(request);
                        String responseMessage = new String(request.getData(), request.getOffset(),
                                request.getLength(), StandardCharsets.UTF_8);
                        if (received = responseMessage.contains(requestMessage)) {
                            System.out.printf("Received '%s'", responseMessage);
                        }
                    } catch (IOException e) {
                        System.err.println("Error while trying to send a request or process a response: "
                                + e.getMessage());
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Unable to establish connection via socket: " + e.getMessage());
        }
    }
}
