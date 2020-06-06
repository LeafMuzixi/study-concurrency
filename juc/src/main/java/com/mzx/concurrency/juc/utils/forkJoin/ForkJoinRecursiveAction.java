package com.mzx.concurrency.juc.utils.forkJoin;

import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ForkJoinRecursiveAction {
    private static final int MAX_THRESHOLD = 3;

    private static final AtomicInteger sum = new AtomicInteger();

    public static void main(String[] args) {
        final ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.execute(new CalculatedRecursiveAction(0, 1000));
        try {
            pool.awaitTermination(10, TimeUnit.MILLISECONDS);
            Optional.of(sum).ifPresent(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class CalculatedRecursiveAction extends RecursiveAction {
        private final int start;

        private final int end;

        private CalculatedRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_THRESHOLD) {
                sum.addAndGet(IntStream.range(start, end).sum());
            } else {
                int middle = (start + end) / 2;
                CalculatedRecursiveAction leftAction = new CalculatedRecursiveAction(start, middle);
                CalculatedRecursiveAction rightAction = new CalculatedRecursiveAction(middle, end);
                leftAction.fork();
                rightAction.fork();
            }
        }
    }
}
