package com.mzx.concurrency.designPattern.requestQueue;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ServerThread extends Thread {
    private final RequestQueue queue;

    private volatile boolean flag = false;

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!flag) {
            Request request = queue.getRequest();
            if (request == null) {
                System.out.println("Received the empty request.");
                continue;
            }
            System.out.println("Server -> " + request.getValue());
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void close() {
        this.flag = true;
        this.interrupt();
    }
}
