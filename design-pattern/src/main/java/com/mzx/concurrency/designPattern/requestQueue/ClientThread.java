package com.mzx.concurrency.designPattern.requestQueue;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ClientThread extends Thread {
    private final RequestQueue queue;

    private final String sendValue;

    public ClientThread(RequestQueue queue, String sendValue) {
        this.queue = queue;
        this.sendValue = sendValue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Client -> request " + sendValue);
            queue.putRequest(new Request(sendValue));
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
