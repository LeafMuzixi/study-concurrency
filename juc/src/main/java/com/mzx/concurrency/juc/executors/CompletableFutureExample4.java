package com.mzx.concurrency.juc.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample4 {
    public static void main(String[] args) throws InterruptedException {
//        thenAcceptBoth().join();
//        acceptEither().join();
//        runAfterBoth().join();
//        runAfterEither().join();
//        combine().join();
        compose().join();
        Thread.currentThread().join();
    }

    private static CompletableFuture<Void> compose() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("start the compose1");
            sleep(5);
            System.out.println("end the compose1");
            return "combine-1";
        }).thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> {
            System.out.println("start the compose2");
            sleep(3);
            System.out.println("end the compose2");
            return s.length();
        })).thenAcceptAsync(System.out::println);
    }

    private static CompletableFuture<Boolean> combine() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("start the combine1");
            sleep(5);
            System.out.println("end the combine1");
            return "combine-1";
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the combine2");
            sleep(5);
            System.out.println("end the combine2");
            return 100;
        }), (s, i) -> s.length() > i).whenCompleteAsync((aBoolean, throwable) -> System.out.println(aBoolean));
    }

    private static CompletableFuture<Void> runAfterEither() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("start the runAfterBoth1");
            sleep(5);
            System.out.println("end the runAfterBoth1");
            return "runAfterBoth-1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the runAfterBoth2");
            sleep(5);
            System.out.println("end the runAfterBoth2");
            return "runAfterBoth-2";
        }), () -> System.out.println("end"));
    }

    private static CompletableFuture<Void> runAfterBoth() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("start the runAfterBoth1");
            sleep(5);
            System.out.println("end the runAfterBoth1");
            return "runAfterBoth-1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the runAfterBoth2");
            sleep(5);
            System.out.println("end the runAfterBoth2");
            return "runAfterBoth-2";
        }), () -> System.out.println("end"));
    }

    private static CompletableFuture<Void> acceptEither() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("start the acceptEither1");
            sleep(5);
            System.out.println("end the acceptEither1");
            return "acceptEither-1";
        }).acceptEitherAsync(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the acceptEither2");
            sleep(3);
            System.out.println("end the acceptEither2");
            return "acceptEither-2";
        }), System.out::println);
    }

    private static CompletableFuture<Void> thenAcceptBoth() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync");
            sleep(5);
            System.out.println("end the supplyAsync");
            return "thenAcceptBoth";
        }).thenAcceptBothAsync(CompletableFuture.supplyAsync(() -> {
            System.out.println("start the thenAcceptBothAsync");
            sleep(5);
            System.out.println("end the thenAcceptBothAsync");
            return 100;
        }), (s, i) -> System.out.println(s + " - " + i));
    }

    private static void sleep(long sleep) {
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
