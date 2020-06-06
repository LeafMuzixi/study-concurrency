package com.mzx.concurrency.juc.executors;

import java.util.concurrent.*;

public class FutureExample1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        testGet();
        testGetWithTimeOut();
    }

    /**
     * {@link Future#get()}
     */
    private static void testGet() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        // ===================================
        System.out.println("I will be printed quickly.");
        // ===================================
        Thread callerThread = Thread.currentThread();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                callerThread.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Integer result = future.get();
        System.out.println(result);
    }

    private static void testGetWithTimeOut() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("====================");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        Integer result = future.get(5, TimeUnit.SECONDS);
        System.out.println(result);
    }
}
