package com.mzx.concurrency.juc.utils.condition;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ConditionExample1 {
    private static final Lock LOCK = new ReentrantLock();

    private static final Condition CONDITION = LOCK.newCondition();

    private static int data = 0;

    private static volatile boolean noUse = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                buildDate();
            }
        }).start();
        IntStream.range(0, 2).forEach(i -> {
            new Thread(() -> {
                while (true) {
                    useDate();
                }
            }).start();
        });
    }

    private static void buildDate() {
        LOCK.lock();
        try {
            while (noUse) {
                CONDITION.await();
            }
            data++;
            Optional.of("P: " + data).ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(1);
            noUse = true;
            CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    public static void useDate() {
        LOCK.lock();
        try {
            while (!noUse) {
                CONDITION.await();
            }
            TimeUnit.SECONDS.sleep(1);
            Optional.of("C: " + data).ifPresent(System.out::println);
            noUse = false;
            CONDITION.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }
}
