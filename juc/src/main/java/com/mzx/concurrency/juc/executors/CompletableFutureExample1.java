package com.mzx.concurrency.juc.executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CompletableFutureExample1 {
    public static void main(String[] args) throws InterruptedException {
//        ExecutorService service = Executors.newFixedThreadPool(10);
//
//        Future<?> future = service.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        while (!future.isDone()) {
//
//        }
//        System.out.println("DONE");

//        CompletableFuture.runAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).whenComplete((v, t) -> System.out.println("DONE"));
//        System.out.println("===============I am not blocked=============");
//        Thread.currentThread().join();

//        ExecutorService service = Executors.newFixedThreadPool(10);
//        List<Callable<Integer>> tasks = IntStream.range(0, 10).boxed().map(i -> (Callable<Integer>) CompletableFutureExample1::get).collect(Collectors.toList());
//        service.invokeAll(tasks).stream().map(future -> {
//            try {
//                return future.get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }).parallel().forEach(CompletableFutureExample1::display);

        IntStream.range(0, 10).boxed().forEach(i ->
                CompletableFuture.supplyAsync(CompletableFutureExample1::get)
                        .thenAccept(CompletableFutureExample1::display)
                        .whenComplete((v, t) -> System.out.println(i + " DONE"))
        );
        Thread.currentThread().join();
    }

    private static void display(int data) {
        int value = ThreadLocalRandom.current().nextInt(20);
        try {
            System.out.println(Thread.currentThread().getName() + " display will be sleep " + value);
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " display execute done " + data);
    }

    private static int get() {
        int value = ThreadLocalRandom.current().nextInt(20);
        try {
            System.out.println(Thread.currentThread().getName() + " get will be sleep " + value);
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " get execute done " + value);
        return value;
    }
}
