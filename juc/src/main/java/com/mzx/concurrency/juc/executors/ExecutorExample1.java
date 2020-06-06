package com.mzx.concurrency.juc.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorExample1 {
    public static void main(String[] args) throws InterruptedException {
//        useCachedThreadPool();
//        useFixedThreadPool();
        useSingleThreadPool();
    }

    private static void useSingleThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
    }

    private static void useFixedThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
        executorService.execute(() -> System.out.println("=============="));
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());

        IntStream.range(0, 100).forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " [" + i + "]");
            });
        });
        TimeUnit.SECONDS.sleep(1);
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
    }

    private static void useCachedThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
        executorService.execute(() -> System.out.println("=============="));
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());

        IntStream.range(0, 100).forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " [" + i + "]");
            });
        });
        TimeUnit.SECONDS.sleep(1);
        System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
    }
}
