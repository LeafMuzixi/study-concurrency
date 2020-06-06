package com.mzx.concurrency.juc.executors;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ScheduledExecutorServiceExample2 {
    public static void main(String[] args) throws InterruptedException {
//        testScheduleWithFixedDelay();
//        testScheduleAtFixedRate();
        testScheduleWithFixedDelay2();
    }

    private static void testScheduleWithFixedDelay() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

        final AtomicLong interval = new AtomicLong(0);
        executor.scheduleWithFixedDelay(() -> {
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

    private static void testScheduleAtFixedRate() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println(executor.getContinueExistingPeriodicTasksAfterShutdownPolicy());
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
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
        TimeUnit.MILLISECONDS.sleep(1200);
        executor.shutdown();
        System.out.println("======over======");
    }

    private static void testScheduleWithFixedDelay2() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println(executor.getExecuteExistingDelayedTasksAfterShutdownPolicy());
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);

        final AtomicLong interval = new AtomicLong(0);
        executor.scheduleWithFixedDelay(() -> {
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
        TimeUnit.MILLISECONDS.sleep(1200);
        executor.shutdown();
        System.out.println("======over======");
    }
}
