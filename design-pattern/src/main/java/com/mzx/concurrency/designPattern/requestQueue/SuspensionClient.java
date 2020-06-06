package com.mzx.concurrency.designPattern.requestQueue;

import java.util.concurrent.TimeUnit;

public class SuspensionClient {
    public static void main(String[] args) throws InterruptedException {
        RequestQueue queue = new RequestQueue();
        final ClientThread clientThread = new ClientThread(queue, "ALEX");
        clientThread.start();
        final ServerThread serverThread = new ServerThread(queue);
        serverThread.start();

        TimeUnit.SECONDS.sleep(10);
        serverThread.close();
    }
}
