package com.mzx.concurrency.juc.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ScheduledExecutorServiceExample1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
//        ScheduledFuture<?> future = executor.schedule(() -> System.out.println("I will be invoked!"), 2, TimeUnit.SECONDS);
//        System.out.println(future.cancel(true));

//        ScheduledFuture<Integer> future = executor.schedule(() -> 2, 2, TimeUnit.SECONDS);
//        System.out.println(future.get());

        final AtomicLong interval = new AtomicLong(0);
        executor.scheduleAtFixedRate(() -> {
            long currentTimeMills = System.currentTimeMillis();
            if (interval.get() == 0) {
                System.out.printf("The first time trigger task at %d\n", currentTimeMills);
            } else {
                System.out.printf("The actually speed [%d]\n", currentTimeMills - interval.get());
            }
            interval.set(currentTimeMills);
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 2, TimeUnit.SECONDS);
    }
}
