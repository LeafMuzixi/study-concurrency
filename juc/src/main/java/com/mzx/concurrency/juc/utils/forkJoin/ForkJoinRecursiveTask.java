package com.mzx.concurrency.juc.utils.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoinRecursiveTask {
    private static final int MAX_THRESHOLD = 200;

    public static void main(String[] args) {
        final ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinTask<Integer> task = pool.submit(new CalculatedRecursiveTask(0, 1000));
        try {
            Integer sum = task.get();
            System.out.println(sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class CalculatedRecursiveTask extends RecursiveTask<Integer> {
        private final int start;

        private final int end;

        private CalculatedRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= MAX_THRESHOLD) {
                return IntStream.range(start, end).sum();
            }
            int middle = (start + end) / 2;
            CalculatedRecursiveTask leftTask = new CalculatedRecursiveTask(start, middle);
            CalculatedRecursiveTask rightTask = new CalculatedRecursiveTask(middle, end);
            leftTask.fork();
            rightTask.fork();
            return leftTask.join() + rightTask.join();
        }
    }
}
