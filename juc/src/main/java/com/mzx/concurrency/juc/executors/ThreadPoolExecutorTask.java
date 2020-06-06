package com.mzx.concurrency.juc.executors;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPoolExecutorTask {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10)
                , Thread::new,
                new ThreadPoolExecutor.AbortPolicy());
        IntStream.range(0, 20).boxed().forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + " [" + i + "] finish done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        List<Runnable> runnableList = null;
        try {
            runnableList = executorService.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        executorService.shutdown();
//        executorService.shutdownNow();
        System.out.println("==================over==================");
        System.out.println(runnableList);
        System.out.println(runnableList.size());
    }
}
