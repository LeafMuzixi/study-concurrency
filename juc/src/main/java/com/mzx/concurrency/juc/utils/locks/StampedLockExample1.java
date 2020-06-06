package com.mzx.concurrency.juc.utils.locks;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

public class StampedLockExample1 {
    private static final int QUEUE_MAX_WAIT = 32;

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("executor-%d").build();

    private static final BlockingQueue<Runnable> BLOCKING_QUEUE = new LinkedBlockingDeque<>(QUEUE_MAX_WAIT);

    private static final ExecutorService SERVICE = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, BLOCKING_QUEUE, THREAD_FACTORY);

    private static final StampedLock LOCK = new StampedLock();

    private static final List<Long> DATA = new ArrayList<>();

    public static void main(String[] args) {
        Runnable readTask = () -> {
            while (true) read();
        };
        Runnable writeTask = () -> {
            while (true) write();
        };

        SERVICE.submit(readTask);
        SERVICE.submit(readTask);
        SERVICE.submit(readTask);
        SERVICE.submit(readTask);
        SERVICE.submit(readTask);
        SERVICE.submit(readTask);
        SERVICE.submit(readTask);
        SERVICE.submit(readTask);
        SERVICE.submit(readTask);
        SERVICE.submit(writeTask);
    }

    private static void read() {
        long stamp = LOCK.readLock();
        try {
            Optional.of(DATA.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", "")))
                    .ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlockRead(stamp);
        }
    }

    private static void write() {
        long stamp = LOCK.writeLock();
        try {
            DATA.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlockWrite(stamp);
        }
    }
}
