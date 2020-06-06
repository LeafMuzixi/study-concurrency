package com.mzx.concurrency.juc.utils.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample2 {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            System.out.println("Do some initial working.");
            try {
                TimeUnit.SECONDS.sleep(1);
                latch.await();
                System.out.println("Do other working...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            System.out.println("Async prepare for some data.");
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Data prepare for done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();
        new Thread(() -> {
            try {
                latch.await();
                System.out.println("Release.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
