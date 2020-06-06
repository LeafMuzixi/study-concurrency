package com.mzx.concurrency.juc.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

//        future.thenRunAsync(() -> System.out.println("done")).get();
//        future.thenAcceptAsync(System.out::println).get();
//        System.out.println(future.handleAsync((s, throwable) -> s.length()).get());
//        System.out.println(future.thenApply(String::length).get());
//        future.whenCompleteAsync((s, throwable) -> System.out.println("end"));
//        future.whenCompleteAsync((s, throwable) -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("=====over=====");
//        });
//        System.out.println(future.get());
//        Thread.currentThread().join();
    }
}
