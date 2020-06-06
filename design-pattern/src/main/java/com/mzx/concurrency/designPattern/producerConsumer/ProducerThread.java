package com.mzx.concurrency.designPattern.producerConsumer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerThread extends Thread {
    private final MessageQueue messageQueue;

    private final static AtomicInteger counter = new AtomicInteger();

    private boolean close = false;

    public ProducerThread(MessageQueue messageQueue, int seq) {
        super("PRODUCER" + seq);
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (!close) {
            try {
                Message message = new Message("Message-" + counter.getAndIncrement());
                messageQueue.put(message);
                System.out.println(Thread.currentThread().getName() + " put message " + message.getData());
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
