package com.mzx.concurrency.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class CompareAndSetLock {
    private final AtomicInteger value = new AtomicInteger(0);

    private Thread lockedThread;

    public void tryLock() throws GetLockException {
        boolean success = value.compareAndSet(0, 1);
        if (!success)
            throw new GetLockException("Get the lock failed.");
        lockedThread = Thread.currentThread();
    }

    public void unlock() {
        if (value.get() == 0) {
            return;
        }
        if (lockedThread == Thread.currentThread())
            value.compareAndSet(1, 0);
    }
}
