package com.mzx.concurrency.juc.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPoolExecutorLongTimeTask {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                },
                new ThreadPoolExecutor.AbortPolicy());

        IntStream.range(0, 10).boxed().forEach(i -> {
            executorService.submit(() -> {
                while (true) {
                }
            });
        });

        executorService.shutdownNow();

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("============start the sequence working============");
    }
}
