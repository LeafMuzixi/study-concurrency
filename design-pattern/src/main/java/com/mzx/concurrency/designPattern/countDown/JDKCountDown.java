package com.mzx.concurrency.designPattern.countDown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class JDKCountDown {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(5);
        System.out.println("准备多线程处理任务");
        // The first phase.
        IntStream.range(0, 5).forEach(value -> {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is working.");
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }, String.valueOf(value)).start();
        });

        latch.await();
        // The second phase.
        System.out.println("多线程任务全部处理完毕，准备第二阶段任务");
        System.out.println("...............");
        System.out.println("FINISH");
    }
}
