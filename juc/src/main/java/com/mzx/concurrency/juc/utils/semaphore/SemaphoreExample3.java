package com.mzx.concurrency.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample3 {
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        Thread thread1 = new Thread(() -> {
            try {
                semaphore.acquire();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            System.out.println("T1 finished.");
        });
        TimeUnit.MILLISECONDS.sleep(50);
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
        TimeUnit.MILLISECONDS.sleep(50);
        thread2.interrupt();
    }
}
