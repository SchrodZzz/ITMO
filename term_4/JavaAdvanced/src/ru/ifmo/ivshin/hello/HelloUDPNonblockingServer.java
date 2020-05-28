package ru.ifmo.ivshin.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloUDPNonblockingServer implements HelloServer {

    Selector selector;
    DatagramChannel channel;

    boolean isClose = false;
    ByteBuffer byteBuffer = ByteBuffer.allocate(64);
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void start(int port, int threads) {
        try {
            selector = Selector.open();
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            channel.bind(new InetSocketAddress(port));
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.submit(this::runtime);
    }

    @Override
    public void close() {
        isClose = true;
        executorService.shutdownNow();
    }

    private void runtime() {
        try {
            while (!isClose) {
                selector.select(this::sendData, 100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendData(SelectionKey selectionKey) {
        try {
            if (selectionKey.isReadable()) {
                SocketAddress address = channel.receive(byteBuffer);
                if (address != null) {
                    String msg = new String(byteBuffer.array(), 0, byteBuffer.position(), StandardCharsets.UTF_8);
                    byte[] data = ("Hello, " + msg).getBytes(StandardCharsets.UTF_8);
                    channel.send(ByteBuffer.wrap(data, 0, data.length), address);
                    byteBuffer.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
