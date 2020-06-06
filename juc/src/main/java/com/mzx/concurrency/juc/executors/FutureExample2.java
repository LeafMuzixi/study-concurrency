package com.mzx.concurrency.juc.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class FutureExample2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testIsDone();
//        testCancel();
        testCancel2();
    }

    private static void testIsDone() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            throw new RuntimeException();
        });
        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Is done " + future.isDone());
        }
//        System.out.println(future.isDone());
    }

    /**
     * Try to cancel maybe failed.
     * <ul>
     *     <li>Task is completed already.</li>
     *     <li>Has already been cancelled.</li>
     * </ul>
     */
    private static void testCancel() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        AtomicBoolean running = new AtomicBoolean(true);
        Future<Integer> future = service.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            while (running.get()) {
            }
            return 10;
        });
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
    }

    private static void testCancel2() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//                System.out.println("=======================");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            while (!Thread.interrupted()) {
            }
            System.out.println("1111111111111111");
            return 10;
        });
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println(future.get());
    }
}
