package com.mzx.concurrency.juc.utils.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock(true);

    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = LOCK.readLock();

    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = LOCK.writeLock();

    private static final List<Long> data = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(ReadWriteLockExample::write);
        thread1.start();
        TimeUnit.SECONDS.sleep(1);
        Thread thread2 = new Thread(ReadWriteLockExample::read);
        thread2.start();
    }

    public static void write() {
        WRITE_LOCK.lock();
        try {
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    public static void read() {
        READ_LOCK.lock();
        try {
            data.forEach(System.out::println);
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + "===============");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            READ_LOCK.unlock();
        }
    }
}
