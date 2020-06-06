package com.mzx.concurrency.designPattern.demo1;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 * 生产者消费者模型
 */
public class ProduceConsumer {
    private static final int SIZE = 1;
    private static final Object LOCK = new Object();
    private int num = 1;
    private final Queue<Integer> queue = new ArrayDeque<>();

    public void produce() {
        synchronized (LOCK) {
            try {
                if (queue.size() < SIZE) {
                    System.out.println(Thread.currentThread().getName() + " produce : " + num);
                    queue.offer(num++);
                } else {
                    LOCK.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consume() {
        synchronized (LOCK) {
            if (!queue.isEmpty()) {
                Optional.ofNullable(queue.poll()).ifPresent(i -> System.out.println(Thread.currentThread().getName() + " consumer : " + i));
            } else {
                LOCK.notifyAll();
            }
        }
    }
}
