package com.mzx.concurrency.juc.executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ExecutorServiceExample3 {
    public static void main(String[] args) throws InterruptedException {
//        test();
//        testAllowCoreThreadTimeOut();
//        testRemove();
//        testPrestartCoreThread();
//        testPrestartAllThread();
        testThreadPoolAdvice();
    }

    private static void test() throws InterruptedException {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        System.out.println(service.getActiveCount());
        service.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println(service.getActiveCount());
    }

    private static void testAllowCoreThreadTimeOut() throws InterruptedException {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        service.setKeepAliveTime(10, TimeUnit.SECONDS);
        service.allowCoreThreadTimeOut(true);
        IntStream.range(0, 5).boxed().forEach(i -> {
            service.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println(service.getActiveCount());
    }

    private static void testRemove() throws InterruptedException {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        service.setKeepAliveTime(10, TimeUnit.SECONDS);
        service.allowCoreThreadTimeOut(true);
        IntStream.range(0, 5).boxed().forEach(i -> {
            service.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("======= I am finished.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.MILLISECONDS.sleep(20);
        Runnable runnable = () -> {
            System.out.println("I will never be executed!");
        };
        service.execute(runnable);
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println(service.remove(runnable));
    }

    private static void testPrestartCoreThread() {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        System.out.println(service.getActiveCount());

        System.out.println(service.prestartCoreThread());
        System.out.println(service.getActiveCount());

        System.out.println(service.prestartCoreThread());
        System.out.println(service.getActiveCount());
        for (int i = 0; i < 2; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println(service.prestartCoreThread());
        System.out.println(service.getActiveCount());
    }

    private static void testPrestartAllThread() {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        service.setMaximumPoolSize(3);
        System.out.println(service.getActiveCount());

        System.out.println(service.prestartAllCoreThreads());
        System.out.println(service.getActiveCount());
    }

    private static void testThreadPoolAdvice() {
        ExecutorService executorService = new MyThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1)
                , Thread::new,
                new ThreadPoolExecutor.AbortPolicy());

        executorService.execute(new MyRunnable(1) {
            @Override
            public void run() {
                System.out.println("======================");
            }
        });

        executorService.execute(new MyRunnable(2) {
            @Override
            public void run() {
                int a = 1 / 0;
            }
        });
    }

    private static abstract class MyRunnable implements Runnable {
        private final int no;

        private MyRunnable(int no) {
            this.no = no;
        }

        protected int getData() {
            return this.no;
        }
    }

    private static class MyThreadPoolExecutor extends ThreadPoolExecutor {
        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("Initial the " + ((MyRunnable) r).getData());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            if (null == t) {
                System.out.println("Successful " + ((MyRunnable) r).getData());
            } else {
                t.printStackTrace();
            }
        }
    }
}
