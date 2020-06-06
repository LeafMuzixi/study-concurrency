package com.mzx.concurrency.juc.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorServiceExample2 {
    public static void main(String[] args) throws InterruptedException {
//        testAbortPolicy();
//        testDiscardPolicy();
//        testCallerRunsPolicy();
        testDiscardOldestRunsPolicy();
    }

    private static void testAbortPolicy() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                Thread::new,
                new ThreadPoolExecutor.AbortPolicy());
        IntStream.range(0, 3).forEach(i -> {
            service.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.SECONDS.sleep(1);
        service.execute(() -> System.out.println("x"));
    }

    private static void testDiscardPolicy() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                Thread::new,
                new ThreadPoolExecutor.DiscardPolicy());
        IntStream.range(0, 3).forEach(i -> {
            service.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.SECONDS.sleep(1);
        service.execute(() -> System.out.println("x"));
        System.out.println("=========================");
    }

    private static void testCallerRunsPolicy() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                Thread::new,
                new ThreadPoolExecutor.CallerRunsPolicy());
        IntStream.range(0, 3).forEach(i -> {
            service.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.SECONDS.sleep(1);
        service.execute(() -> {
            System.out.println("x");
            System.out.println(Thread.currentThread().getName());
        });
        System.out.println("=========================");
    }

    private static void testDiscardOldestRunsPolicy() throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                Thread::new,
                new ThreadPoolExecutor.DiscardOldestPolicy());
        IntStream.range(0, 3).forEach(i -> {
            service.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("I come from lambda.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.SECONDS.sleep(1);
        service.execute(() -> {
            System.out.println("x");
            System.out.println(Thread.currentThread().getName());
        });
        System.out.println("=========================");
    }
}
