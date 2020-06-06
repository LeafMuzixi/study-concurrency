package com.mzx.concurrency.juc.executors;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutorServiceExample4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        testInvokeAny();
//        testInvokeAnyTimeOut();
//        testInvokeAll();
//        testInvokeAllTimeOut();
//        testSubmitRunnable();
        testSubmitRunnableWithResult();
    }

    /**
     * Question:
     * When the result returned, other callable will be keep on process?
     * <p>
     * Answer:
     * Other callable will be canceled (if other not process finished).
     * <p>
     * Note:
     * The invokeAny will be blocked caller.
     * <p>
     * {@link ExecutorService#invokeAny(Collection)}
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testInvokeAny() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> callableList = IntStream.range(0, 5).boxed().map(i ->
                (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                    System.out.println(Thread.currentThread().getName() + " :" + i);
                    return i;
                }).collect(Collectors.toList());
        Integer any = service.invokeAny(callableList);
        System.out.println("=============finished=============");
        System.out.println(any);
    }

    /**
     * {@link ExecutorService#invokeAny(Collection, long, TimeUnit)}
     */
    private static void testInvokeAnyTimeOut() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Integer any = service.invokeAny(IntStream.range(0, 5).boxed().map(i ->
                (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                    System.out.println(Thread.currentThread().getName() + " :" + i);
                    return i;
                }).collect(Collectors.toList()), 3, TimeUnit.SECONDS);
        System.out.println("=============finished=============");
        System.out.println(any);
    }

    /**
     * {@link ExecutorService#invokeAll(Collection)}
     */
    private static void testInvokeAll() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.invokeAll(IntStream.range(0, 5).boxed().map(i ->
                (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + " :" + i);
                    return i;
                }).collect(Collectors.toList()))
                .parallelStream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).forEach(System.out::println);
        System.out.println("=============finished=============");
    }

    /**
     * {@link ExecutorService#invokeAll(Collection, long, TimeUnit)}
     */
    private static void testInvokeAllTimeOut() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.invokeAll(IntStream.range(0, 5).boxed().map(i ->
                (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + " :" + i);
                    return i;
                }).collect(Collectors.toList()), 1, TimeUnit.SECONDS)
                .parallelStream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).forEach(System.out::println);
        System.out.println("=============finished=============");
    }

    /**
     * {@link ExecutorService#submit(Runnable)}
     */
    private static void testSubmitRunnable() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<?> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Object result = future.get();
        System.out.println("R: " + result);
    }

    /**
     * {@link ExecutorService#submit(Runnable, Object)}
     */
    private static void testSubmitRunnableWithResult() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        String result = "done.";
        Future<?> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, result);
        System.out.println(future.get());
    }
}
