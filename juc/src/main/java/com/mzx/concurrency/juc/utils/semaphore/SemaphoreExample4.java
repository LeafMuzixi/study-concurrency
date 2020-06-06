package com.mzx.concurrency.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample4 {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(5);
        Thread thread1 = new Thread(() -> {
            try {
                semaphore.drainPermits();
                System.out.println(semaphore.availablePermits());
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(5);
            }
            System.out.println("T1 finished.");
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            try {
                semaphore.acquire();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            System.out.println("T2 finished.");
        });
        thread2.start();
    }
}
