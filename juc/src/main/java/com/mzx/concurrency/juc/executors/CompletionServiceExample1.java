package com.mzx.concurrency.juc.executors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Future defect
 */
public class CompletionServiceExample1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        futureDefect2();
    }

    /**
     * No callback
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void futureDefect1() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        System.out.println("===================");
        future.get();
    }

    /**
     * The batch task can not get result in turn.
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void futureDefect2() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        final List<Callable<Integer>> callableList = Arrays.asList(() -> {
            sleep(10);
            System.out.println("The 10 finished.");
            return 1;
        }, () -> {
            sleep(20);
            System.out.println("The 20 finished.");
            return 2;
        });
//        List<Future<Integer>> futures = service.invokeAll(callableList);
//        Integer v1 = futures.get(0).get();
//        System.out.println(v1);
//        Integer v2 = futures.get(1).get();
//        System.out.println(v2);

        List<Future<Integer>> futures = new ArrayList<>();
        futures.add(service.submit(callableList.get(0)));
        futures.add(service.submit(callableList.get(1)));
        for (Future<Integer> future : futures) {
            System.out.println(future.get());
        }
    }

    private static void sleep(long sleep) {
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
