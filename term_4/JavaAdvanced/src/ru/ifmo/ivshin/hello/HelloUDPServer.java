package ru.ifmo.ivshin.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloUDPServer implements HelloServer {
    private DatagramSocket socket;
    private ExecutorService mainExecutor;
    private ExecutorService workers;
    private int bufferSize = 0;

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.out.println("Wrong arguments");
            return;
        }
        try {
            try (final HelloServer server = new HelloUDPServer()) {
                server.start(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            }
        } catch (NumberFormatException e) {
            System.out.println("Numeric arguments parse error: " + e.getMessage());
        }

    }

    @Override
    public void start(int port, int threads) {
        try {
            socket = new DatagramSocket(port);
            bufferSize = socket.getReceiveBufferSize();
            workers = Executors.newFixedThreadPool(threads);
            mainExecutor = Executors.newSingleThreadExecutor();
            mainExecutor.submit(this::receiveAndRespond);
        } catch (SocketException e) {
            System.err.println("Unable to establish connection via socket: " + e.getMessage());
        }
    }

    private void receiveAndRespond() {
        try {
            while (!socket.isClosed() && !Thread.interrupted()) {
                final DatagramPacket request = new DatagramPacket(new byte[bufferSize], bufferSize);
                socket.receive(request);

                workers.submit(() -> {
                    final String requestMessage = new String(request.getData(),
                            request.getOffset(), request.getLength(), StandardCharsets.UTF_8);
                    String responseMessage = "Hello, " + requestMessage;
                    request.setData(responseMessage.getBytes(StandardCharsets.UTF_8));
                    try {
                        socket.send(request);
                    } catch (IOException e) {
                        if (!socket.isClosed()) {
                            System.err.println("Error while trying to send a response: " + e.getMessage());
                        }
                    }
                });
            }
        } catch (IOException e) {
            if (!socket.isClosed()) {
                System.err.println("Error while trying to receive a request: " + e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        socket.close();
        mainExecutor.shutdown();
        workers.shutdown();
        try {
            mainExecutor.awaitTermination(1, TimeUnit.SECONDS);
            workers.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Could not terminate executor pools: " + e.getMessage());
        }
    }
}
