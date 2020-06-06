package com.mzx.concurrency.juc.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorBuild {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) buildThreadPoolExecutor();

        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != executor.getActiveCount() || queueSize != executor.getQueue().size()) {
                System.out.println(executor.getActiveCount());
                System.out.println(executor.getCorePoolSize());
                System.out.println(executor.getQueue().size());
                System.out.println(executor.getMaximumPoolSize());
                activeCount = executor.getActiveCount();
                queueSize = executor.getQueue().size();
                System.out.println("===============================================");
            }
        }
    }

    private static ExecutorService buildThreadPoolExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1)
                , Thread::new,
                new ThreadPoolExecutor.AbortPolicy());
        System.out.println("The ThreadPoolExecutor create done.");

        executorService.execute(() -> sleep(100));
        executorService.execute(() -> sleep(10));
        executorService.execute(() -> sleep(10));
        return executorService;
    }

    private static void sleep(long sleep) {
        try {
            System.out.println("* " + Thread.currentThread().getName() + " *");
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
