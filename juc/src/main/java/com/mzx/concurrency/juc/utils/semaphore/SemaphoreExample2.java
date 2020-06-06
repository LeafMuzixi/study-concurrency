package com.mzx.concurrency.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreExample2 {
    /**
     * connection poll
     * When get the not available connection/policy
     * 1.Get 1000MS then throw exception
     * 2.blocking
     * 3.discard
     * 4.Get then throw exception
     * 5.get -> register the callback -> call you
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(2);
        IntStream.range(0, 3).forEach(i -> {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " in");
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " get the semaphore.");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
                System.out.println(Thread.currentThread().getName() + " out");
            }).start();
        });
        while (true) {
            System.out.println("AP->" + semaphore.availablePermits());
            System.out.println("QL->" + semaphore.getQueueLength());
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
