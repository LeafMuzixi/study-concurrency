package com.mzx.concurrency.designPattern.pool;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 简单线程池实现
 */
public class SimpleThreadPool extends Thread {
    private final static int DEFAULT_TASK_QUEUE_SIZE = 30;
    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";
    private final static ThreadGroup THREAD_GROUP = new ThreadGroup("PoolGroup");
    private final static Queue<Runnable> TASK_QUEUE = new LinkedList<>();
    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();
    private final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("Discard This Task.");
    };

    private final int min;
    private final int max;
    private final int active;
    private final int taskQueueSize;
    private final DiscardPolicy discardPolicy;

    private volatile boolean destroy = false;

    private static volatile int count = 0;
    private static volatile int seq = 0;

    public SimpleThreadPool() {
        this(4, 12, 8, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int max, int active, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.max = max;
        this.active = active;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        for (int i = 0; i < min; i++) {
            createWorkTask();
        }
        this.start();
    }

    private void createWorkTask() {
        WorkerTask workerTask = new WorkerTask(THREAD_GROUP, THREAD_PREFIX + seq++);
        workerTask.start();
        THREAD_QUEUE.add(workerTask);
    }

    public void shutdown() throws InterruptedException {
        while (!TASK_QUEUE.isEmpty()) {
            TimeUnit.MILLISECONDS.sleep(50);
        }
        // 关闭打断所有线程
        int alive = 0;
        while (alive != THREAD_QUEUE.size()) {
            alive = 0;
            synchronized (THREAD_QUEUE) {
                for (WorkerTask task : THREAD_QUEUE) {
                    // 如果线程存活
                    if (task.isAlive()) {
                        alive++;
                    }
                    task.close();
                    if (task.getTaskState() == TaskState.WAITING) {
                        task.interrupt();
                    }
                }
            }
        }
        destroy = true;
        System.out.println("The thread pool disposed.");
    }

    public boolean isDestroy() {
        return destroy;
    }

    public int getSize() {
        return THREAD_QUEUE.size();
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getActive() {
        return active;
    }

    public int getTaskQueueSize() {
        return taskQueueSize;
    }

    public void submit(Runnable runnable) throws DiscardException {
        if (destroy)
            throw new IllegalStateException("The thread pool already destroy and not allow submit task.");
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() >= taskQueueSize) {
                discardPolicy.discard();
            }
            TASK_QUEUE.offer(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    @Override
    public void run() {
        while (!destroy) {
            System.out.printf("Pool#Min:%d,Max:%d,Active:%d,Current:%d,QueueSize:%d\n", this.min, this.max, this.active, THREAD_QUEUE.size(), TASK_QUEUE.size());
            try {
                TimeUnit.SECONDS.sleep(1);
                if (TASK_QUEUE.size() > max && THREAD_QUEUE.size() < max) {
                    IntStream.range(THREAD_QUEUE.size(), max).forEach(value -> createWorkTask());
                    System.out.println("The pool incremented to max.");
                }
                if (TASK_QUEUE.size() > active && THREAD_QUEUE.size() < active) {
                    IntStream.range(THREAD_QUEUE.size(), active).forEach(value -> createWorkTask());
                    System.out.println("The pool incremented to active.");
                }
                if (TASK_QUEUE.isEmpty() && THREAD_QUEUE.size() > active) {
                    int release = THREAD_QUEUE.size() - active;
                    System.out.println("The pool will be release some tasks that are BLOCKED or DEAD.");
                    outer:
                    while (release > 0) {
                        synchronized (THREAD_QUEUE) {
                            Iterator<WorkerTask> iterator = THREAD_QUEUE.iterator();
                            while (iterator.hasNext()) {
                                if (release <= 0) {
                                    break outer;
                                }
                                WorkerTask task = iterator.next();
                                if (task.getTaskState() == TaskState.DEAD) {
                                    iterator.remove();
                                    release--;
                                }
                            }
                            iterator = THREAD_QUEUE.iterator();
                            int needClose = release;
                            while (needClose > 0 && iterator.hasNext()) {
                                WorkerTask task = iterator.next();
                                if (task.taskState == TaskState.WAITING) {
                                    task.close();
                                    task.interrupt();
                                    needClose--;
                                }
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private enum TaskState {
        FREE, RUNNING, WAITING, DEAD
    }

    public static class DiscardException extends RuntimeException {
        public DiscardException(String message) {
            super(message);
        }
    }

    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    private static class WorkerTask extends Thread {
        private volatile TaskState taskState = TaskState.FREE;
        private boolean close;

        public WorkerTask(ThreadGroup group, String name) {
            super(group, name);
            this.close = false;
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        public void run() {
            outer:
            while (!this.close) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            this.taskState = TaskState.WAITING;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break outer;
                        }
                    }
                    runnable = TASK_QUEUE.poll();
                }
                if (runnable != null) {
                    this.taskState = TaskState.RUNNING;
                    runnable.run();
                    count++;
                    System.out.println(count + " task has be processed.");
                    this.taskState = TaskState.FREE;
                }
            }
            this.taskState = TaskState.DEAD;
        }

        public void close() {
            this.close = true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool pool = new SimpleThreadPool();
        IntStream.rangeClosed(1, 50)
                .forEach(value -> {
                    try {
                        pool.submit(() -> {
                            ThreadLocalRandom random = ThreadLocalRandom.current();
                            int time = random.nextInt(10);
                            System.out.println("The runnable " + value + " be serviced by " + Thread.currentThread() + " started.Expected " + time + "ms.");
                            try {
                                TimeUnit.SECONDS.sleep(time);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("The runnable " + value + " be serviced by " + Thread.currentThread() + " finished.");
                        });
                    } catch (DiscardException e) {
                        System.out.println("The " + value + " task has be discarded.");
                    }
                });
        TimeUnit.SECONDS.sleep(10);
        pool.shutdown();
//        pool.submit(() -> {
//            System.out.println("----------");
//        });
    }
}
