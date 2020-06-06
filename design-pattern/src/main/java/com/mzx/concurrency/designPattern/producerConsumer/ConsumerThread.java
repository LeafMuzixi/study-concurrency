package com.mzx.concurrency.designPattern.producerConsumer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ConsumerThread extends Thread {
    private final MessageQueue messageQueue;

    private boolean close = false;

    public ConsumerThread(MessageQueue messageQueue, int seq) {
        super("CONSUMER" + seq);
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (!close) {
            try {
                Message message = messageQueue.take();
                System.out.println(Thread.currentThread().getName() + " take a message: " + message.getData());
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void close() {
        this.close = true;
    }
}
