package com.mzx.concurrency.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * {@link Semaphore} can be used control flow
 */
public class SemaphoreExample1 {
    public static void main(String[] args) {
        final SemaphoreLock lock = new SemaphoreLock();
        IntStream.range(0, 2).forEach(i -> {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " is running...");

                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " get the #SemaphoreLock");
                    TimeUnit.SECONDS.sleep(9);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println(Thread.currentThread().getName() + " release the #SemaphoreLock");
            }).start();
        });
    }

    static class SemaphoreLock {
        private final Semaphore semaphore = new Semaphore(2);

        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock() {
            semaphore.release();
        }
    }
}
