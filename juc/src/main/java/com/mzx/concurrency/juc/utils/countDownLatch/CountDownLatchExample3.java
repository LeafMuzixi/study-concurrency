package com.mzx.concurrency.juc.utils.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample3 {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Thread mainThread = Thread.currentThread();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
//            mainThread.interrupt();
        }).start();

        latch.await(1000, TimeUnit.MILLISECONDS);
        System.out.println("==================");
    }
}
