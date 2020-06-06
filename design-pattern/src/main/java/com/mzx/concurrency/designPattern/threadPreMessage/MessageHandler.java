package com.mzx.concurrency.designPattern.threadPreMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MessageHandler {
    private final static ExecutorService executor = Executors.newFixedThreadPool(5);

    public void request(Message message) {
        executor.execute(() -> {
            String value = message.getValue();
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
                System.out.println("The message will be handle by " + Thread.currentThread().getName() + ". Message : " + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
