package com.mzx.concurrency.juc.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        supplyAsync();
//        runAsync().get();
//        completed("Hello").get();
//        System.out.println(anyOf().get());
        allOf().join();
//        Thread.currentThread().join();
    }

    private static void create() {
        CompletableFuture<String> future = new CompletableFuture<>();
        String s = null;
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> s);
    }

    private static CompletableFuture<Void> allOf() {
        return CompletableFuture.allOf(CompletableFuture.runAsync(() -> {
                    try {
                        System.out.println("1 ====== start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("1 ====== end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).whenComplete((aVoid, throwable) -> System.out.println("=====over=====")),
                CompletableFuture.supplyAsync(() -> {
                    try {
                        System.out.println("2 ====== start");
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println("2 ====== end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Hello";
                }).whenComplete((s, throwable) -> System.out.println("=====" + s + "=====")));
    }

    private static CompletableFuture<Object> anyOf() {
        return CompletableFuture.anyOf(CompletableFuture.runAsync(() -> {
                    try {
                        System.out.println("1 ====== start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("1 ====== end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).whenComplete((aVoid, throwable) -> System.out.println("=====over=====")),
                CompletableFuture.supplyAsync(() -> {
                    try {
                        System.out.println("2 ====== start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("2 ====== end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Hello";
                }).whenComplete((s, throwable) -> System.out.println("=====" + s + "=====")));
    }

    private static CompletableFuture<Void> completed(String data) {
        return CompletableFuture.completedFuture(data).thenAcceptAsync(System.out::println);
    }

    private static CompletableFuture<Void> runAsync() {
        return CompletableFuture.runAsync(() -> {
            try {
                System.out.println("obj ====== start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("obj ====== end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> System.out.println("=====over====="));
    }

    /**
     * <pre>
     * post -> {
     *     basic
     *     details
     * }
     *
     * insert basic
     * insert details
     *              insert basic
     * [submit]                     ==> action
     *              insert details
     * </pre>
     */
    private static void supplyAsync() {
        CompletableFuture.supplyAsync(Object::new)
                .thenAcceptAsync(o -> {
                    try {
                        System.out.println("obj ====== start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("obj ====== end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).runAfterBoth(CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptAsync(s -> {
                    try {
                        System.out.println("string ====== start");
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("string ====== end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }), () -> System.out.println("=======finished======="));
    }
}
