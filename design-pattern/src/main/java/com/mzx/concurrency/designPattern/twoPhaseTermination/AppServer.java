package com.mzx.concurrency.designPattern.twoPhaseTermination;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer extends Thread {
    private int port;

    private final static int DEFAULT_PORT = 12722;

    private volatile boolean start = true;

    private final List<ClientHandler> clientHandlers = new ArrayList<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private ServerSocket serverSocket;

    public AppServer() {
        this(DEFAULT_PORT);
    }

    public AppServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(port);
            while (start) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                this.clientHandlers.add(clientHandler);
                this.executor.submit(clientHandler);
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
        } finally {
            this.dispose();
        }
    }

    private void dispose() {
        this.clientHandlers.forEach(ClientHandler::stop);
        this.executor.shutdown();
    }

    public void shutdown() throws IOException {
        this.start = false;
        this.interrupt();
        this.serverSocket.close();
    }
}
