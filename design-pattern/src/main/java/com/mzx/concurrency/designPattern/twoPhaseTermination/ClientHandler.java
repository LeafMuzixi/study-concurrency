package com.mzx.concurrency.designPattern.twoPhaseTermination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    private volatile boolean running = true;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream())) {
            while (running) {
                String message = bufferedReader.readLine();
                if (message == null) {
                    break;
                }
                System.out.println("Come from client -> " + message);
                printWriter.write("echo " + message + "\n");
                printWriter.flush();
            }
        } catch (IOException e) {
            this.running = false;
        } finally {
            this.stop();
        }
    }

    public void stop() {
        if (!running) {
            return;
        }
        this.running = false;
        try {
            this.socket.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
}
