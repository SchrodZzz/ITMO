package ru.ifmo.ivshin.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

public class HelloUDPNonblockingClient implements HelloClient {

    private ByteBuffer byteBuffer = ByteBuffer.allocate(64);
    private String prefix = "";
    private int workers = 0;

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        this.prefix = prefix;
        try (Selector selector = Selector.open()) {
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(host), port);
            for (int i = 0; i != threads; ++i) {
                DatagramChannel channel = DatagramChannel.open();
                channel.configureBlocking(false);
                channel.connect(socketAddress);
                channel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, new Attachment(i, requests, 0));
            }
            while (workers != threads) {
                if (selector.select(this::receiveData, 100) == 0) {
                    selector.keys().forEach(key -> key.interestOps(SelectionKey.OP_WRITE));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveData(SelectionKey selectionKey) {
        try {
            Attachment attachment = (Attachment) selectionKey.attachment();
            String msg = prefix + (attachment.threads) + "_" + attachment.counter;
            if (selectionKey.isReadable()) {
                byteBuffer.clear();
                int size = ((ReadableByteChannel) selectionKey.channel()).read(byteBuffer);
                if (size > 0) {
                    String answer = new String(byteBuffer.array(), 0, size, StandardCharsets.UTF_8);
                    if (answer.endsWith(msg) && answer.startsWith("Hello, ") && answer.length() == (msg.length() + 7)) {
                        attachment.counter += 1;
                    }
                }
                selectionKey.interestOps(SelectionKey.OP_WRITE);
            } else if (selectionKey.isWritable() && attachment.counter >= attachment.max) {
                workers += 1;
                selectionKey.cancel();
                selectionKey.channel().close();
            } else {
                byte[] data = msg.getBytes(StandardCharsets.UTF_8);
                ((WritableByteChannel) selectionKey.channel()).write(ByteBuffer.wrap(data, 0, data.length));
                selectionKey.interestOps(SelectionKey.OP_READ);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Attachment {
        int threads;
        int max;
        int counter;

        public Attachment(int threads, int max, int counter) {
            this.threads = threads;
            this.max = max;
            this.counter = counter;
        }
    }
}
