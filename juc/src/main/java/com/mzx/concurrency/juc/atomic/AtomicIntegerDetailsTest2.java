package com.mzx.concurrency.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class AtomicIntegerDetailsTest2 {
    private static final CompareAndSetLock LOCK = new CompareAndSetLock();

    public static void main(String[] args) {
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                try {
//                    doSomeThing();
                    doSomeThing2();
                } catch (InterruptedException | GetLockException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    public static void doSomeThing() throws InterruptedException {
        synchronized (AtomicIntegerDetailsTest2.class) {
            System.out.println(Thread.currentThread().getName() + " get the lock.");
            TimeUnit.SECONDS.sleep(100);
        }
    }

    public static void doSomeThing2() throws InterruptedException, GetLockException {
        try {
            LOCK.tryLock();
            System.out.println(Thread.currentThread().getName() + " get the lock.");
            TimeUnit.SECONDS.sleep(100);
        } finally {
            LOCK.unlock();
        }
    }
}
