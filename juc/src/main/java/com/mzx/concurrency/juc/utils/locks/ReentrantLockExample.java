package com.mzx.concurrency.juc.utils.locks;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 */
public class ReentrantLockExample {
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
//        IntStream.range(0, 3).forEach(i -> {
//            new Thread(ReentrantLockExample1::needLockBySync).start();
//        });
        Thread thread1 = new Thread(ReentrantLockExample::testUnInterruptibility);
        thread1.start();
        TimeUnit.SECONDS.sleep(1);
        Thread thread2 = new Thread(ReentrantLockExample::testTryLock);
        thread2.start();
        TimeUnit.SECONDS.sleep(1);
        thread2.interrupt();
        System.out.println("========================");
    }

    public static void testTryLock() {
        boolean tryLock = lock.tryLock();
        if (!tryLock) {
            System.out.println("return");
            return;
        }
        try {
            Optional.of("The thread" + Thread.currentThread().getName() + " get lock and will do working.")
                    .ifPresent(System.out::println);
            while (true) {

            }
        } finally {
            lock.unlock();
        }
    }

    public static void testUnInterruptibility() {
        try {
            lock.lockInterruptibly();
            Optional.of("The thread" + Thread.currentThread().getName() + " get lock and will do working.")
                    .ifPresent(System.out::println);
            while (true) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void needLock() {
        lock.lock();
        try {
            Optional.of("The thread" + Thread.currentThread().getName() + " get lock and will do working.")
                    .ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void needLockBySync() {
        synchronized (ReentrantLockExample.class) {
            try {
                Optional.of("The thread" + Thread.currentThread().getName() + " get lock and will do working.")
                        .ifPresent(System.out::println);
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
