package com.mzx.concurrency.juc.utils.condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ConditionExample2 {
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Condition PRODUCE_CONDITION = LOCK.newCondition();

    private static final Condition CONSUME_CONDITION = LOCK.newCondition();

    private static final Queue<Long> TIMESTAMP_QUEUE = new LinkedList<>();

    private static final int MAX_CAPACITY = 100;

    public static void main(String[] args) {
        IntStream.range(0, 5).boxed().forEach(ConditionExample2::beginProduce);
        IntStream.range(0, 5).boxed().forEach(ConditionExample2::beginConsume);
    }

    private static void beginProduce(int n) {
        new Thread(() -> {
            while (true) {
                produce();
                sleep(2);
            }
        }, "P-" + n).start();
    }

    private static void beginConsume(int n) {
        new Thread(() -> {
            while (true) {
                consume();
                sleep(3);
            }
        }, "C-" + n).start();
    }

    private static void produce() {
        LOCK.lock();
        try {
            while (TIMESTAMP_QUEUE.size() >= MAX_CAPACITY) {
                PRODUCE_CONDITION.await();
            }
            long value = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "-P " + value);
            TIMESTAMP_QUEUE.offer(value);
            CONSUME_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    private static void consume() {
        LOCK.lock();
        try {
            while (TIMESTAMP_QUEUE.isEmpty()) {
                CONSUME_CONDITION.await();
            }
            Long value = TIMESTAMP_QUEUE.poll();
            System.out.println(Thread.currentThread().getName() + "-C " + value);
            PRODUCE_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    private static void sleep(long sleepTime) {
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
