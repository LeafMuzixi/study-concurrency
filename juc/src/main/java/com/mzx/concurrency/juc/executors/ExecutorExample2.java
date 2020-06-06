package com.mzx.concurrency.juc.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutorExample2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool();
//        System.out.println(Runtime.getRuntime().availableProcessors());
        List<Callable<String>> callableList = IntStream.range(0, 24).boxed().map(i ->
                (Callable<String>) () -> {
                    System.out.println("Thread " + Thread.currentThread().getName());
                    sleep(2);
                    return "Task-" + i;
                }).collect(Collectors.toList());
        executorService.invokeAll(callableList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).forEach(System.out::println);
    }

    private static void sleep(long sleep) {
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
