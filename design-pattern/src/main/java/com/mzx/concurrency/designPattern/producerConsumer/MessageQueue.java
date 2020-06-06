package com.mzx.concurrency.designPattern.producerConsumer;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private final Queue<Message> queue = new LinkedList<>();

    private final static int DEFAULT_MAX_LIMIT = 100;

    private final int limit;

    public MessageQueue() {
        this(DEFAULT_MAX_LIMIT);
    }

    public MessageQueue(int limit) {
        this.limit = limit;
    }

    public void put(Message message) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() > limit) {
                queue.wait();
            }
            queue.offer(message);
            queue.notifyAll();
        }
    }

    public Message take() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            Message message = queue.poll();
            queue.notifyAll();
            return message;
        }
    }

    public int getMaxLimit() {
        return this.limit;
    }

    public int getMessageSize() {
        synchronized (queue) {
            return queue.size();
        }
    }
}
