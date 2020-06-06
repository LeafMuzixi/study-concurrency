package com.mzx.concurrency.juc.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        getNow();
//        complete();
//        testJoin();
//        completeExceptionally();
//        test();
        CompletableFuture<String> future = errorHandle();
        System.out.println(future.join());
        Thread.currentThread().join();
    }

    private static CompletableFuture<String> errorHandle() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("I will be still process...");
            return "Hello";
        });
        future.thenApply(s -> {
            Integer.parseInt(s);
            System.out.println("================keep move===============");
            return s + " World";
        }).exceptionally(Throwable::getMessage).thenAccept(System.out::println);
        return future;
    }

    private static void test() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("I will be still process...");
            return "Hello";
        });
        future.obtrudeException(new Exception("I am error"));
        System.out.println(future.get());
    }

    private static void completeExceptionally() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("I will be still process...");
            return "Hello";
        });
        future.completeExceptionally(new RuntimeException());
        System.out.println(future.get());
    }

    private static void testJoin() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("I will be still process...");
            return "Hello";
        });
        String result = future.join();
        System.out.println(result);
    }

    private static void complete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            System.out.println("I will be still process...");
            return "Hello";
        });
        sleep(6);
        boolean finished = future.complete("World");
        System.out.println(finished);
        System.out.println(future.get());
    }

    private static void getNow() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5);
            return "Hello";
        });
        String result = future.getNow("World");
        System.out.println(result);
        System.out.println(future.get());
    }

    private static void sleep(long sleep) {
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
