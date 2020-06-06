package com.mzx.concurrency.juc.utils.countDownLatch;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CountDownLatchExample1 {

    private static final int QUEUE_MAX_WAIT = 32;

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("executor-%d").build();

    private static final BlockingQueue<Runnable> BLOCKING_QUEUE = new LinkedBlockingDeque<>(QUEUE_MAX_WAIT);

    private static final ExecutorService SERVICE = new ThreadPoolExecutor(2, 2, 30, TimeUnit.SECONDS, BLOCKING_QUEUE, THREAD_FACTORY);

    private static final CountDownLatch LATCH = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        int[] data = query();
        IntStream.range(0, data.length).forEach(i -> {
            SERVICE.execute(new SimpleRunnable(data, i, LATCH));
        });
        LATCH.await();
        System.out.println("all of work finish done.");
        SERVICE.shutdown();
//        SERVICE.awaitTermination(15, TimeUnit.SECONDS);
    }

    private static int[] query() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    }

    static class SimpleRunnable implements Runnable {
        private final int[] data;

        private final int index;

        private final CountDownLatch latch;

        SimpleRunnable(int[] data, int index, CountDownLatch latch) {
            this.data = data;
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int value = data[index];
            data[index] = value * (value % 2 == 0 ? 2 : 10);
            System.out.println(Thread.currentThread().getName() + "finished.");
            latch.countDown();
        }
    }
}
