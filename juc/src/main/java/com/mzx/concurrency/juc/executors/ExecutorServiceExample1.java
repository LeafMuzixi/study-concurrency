package com.mzx.concurrency.juc.executors;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * The demo for {@link java.util.concurrent.ExecutorService}
 */
public class ExecutorServiceExample1 {
    public static void main(String[] args) throws InterruptedException {
//        isShutdown();
//        isTerminated();
//        executeRunnableError();
        executeRunnableTask();
    }

    /**
     * Question:
     * <p>
     * When invoked the shutdown method,can execute the new runnable?
     * Answer:
     * No!!!The executor service will rejected after shutdown.
     * {@link ExecutorService#isShutdown()}
     * {@link ExecutorService#shutdown()}
     * </p>
     */
    private static void isShutdown() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(executorService.isShutdown());
        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        executorService.execute(() -> System.out.println("I will be executed after shutdown."));
    }

    /**
     * {@link ExecutorService#isTerminated()}
     * {@link ThreadPoolExecutor#isTerminating()}
     */
    private static void isTerminated() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        System.out.println(executorService.isTerminated());
        System.out.println(((ThreadPoolExecutor) executorService).isTerminating());
    }

    private static void executeRunnableError() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory());
        IntStream.range(0, 10).boxed().forEach(i -> executorService.execute(() -> System.out.println(1 / 0)));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("======================");
    }

    private static class MyThreadFactory implements ThreadFactory {
        private static final AtomicInteger SEQ = new AtomicInteger(0);

        @Override
        public Thread newThread(@NotNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("My-Thread-" + SEQ.getAndIncrement());
            thread.setUncaughtExceptionHandler((t, throwable) -> {
                System.out.println("The thread " + t.getName() + " execute failed.");
                throwable.printStackTrace();
                System.out.println("++++++++++++++++++++++++++++++++");
            });
            return thread;
        }
    }

    /**
     * <pre>
     *                                              |---->
     *                                              |---->
     *     send request ----> store db ----> 10 ->  |---->
     *                                              |---->
     *                                              |---->
     * </pre>
     */
    private static void executeRunnableTask() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory());
        IntStream.range(0, 10).boxed().forEach(i -> executorService.execute(new MyTask(i) {
            @Override
            protected void error(Throwable throwable) {
                System.out.println("The no: " + no + " failed, update status to ERROR.");
            }

            @Override
            protected void done() {
                System.out.println("The no: " + no + " successfully, update status to DONE.");
            }

            @Override
            protected void doExecute() {
                if ((i & 3) == 0) {
                    int tmp = i / 0;
                }
            }

            @Override
            protected void doInit() {
                // do something
            }
        }));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("======================");
    }

    private abstract static class MyTask implements Runnable {
        protected final int no;

        private MyTask(int no) {
            this.no = no;
        }

        @Override
        public void run() {
            try {
                this.doInit();
                this.doExecute();
                this.done();
            } catch (Throwable throwable) {
                this.error(throwable);
            }
        }

        protected abstract void error(Throwable throwable);

        protected abstract void done();

        protected abstract void doExecute();

        protected abstract void doInit();
    }
}
