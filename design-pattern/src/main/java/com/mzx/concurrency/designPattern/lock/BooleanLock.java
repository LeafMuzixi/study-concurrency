package com.mzx.concurrency.designPattern.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class BooleanLock implements Lock {
    // The initValue is true indicated the lock have be get.
    // The initValue is false indicated the lock is free(other thread can get this).
    private boolean initValue;

    private final Collection<Thread> blockedThreadCollection = new ArrayList<>();

    private Thread currentThread;

    public BooleanLock() {
        this.initValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while (initValue) {
            this.blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }
        this.currentThread = Thread.currentThread();
        this.initValue = true;
        this.blockedThreadCollection.remove(Thread.currentThread());
    }

    @Override
    public synchronized void lock(long waitMaxMilliseconds) throws InterruptedException, TimeOutException {
        if (waitMaxMilliseconds < 0) {
            lock();
        }
        long endTime = System.currentTimeMillis() + waitMaxMilliseconds;
        while (initValue) {
            this.blockedThreadCollection.add(Thread.currentThread());
            this.wait(waitMaxMilliseconds);
            if (System.currentTimeMillis() > endTime) {
                throw new TimeOutException("Time out");
            }
        }
        this.currentThread = Thread.currentThread();
        this.initValue = true;
        this.blockedThreadCollection.remove(Thread.currentThread());
    }

    @Override
    public synchronized void unlock() {
        if (this.currentThread == Thread.currentThread()) {
            this.initValue = false;
            Optional.of(Thread.currentThread() + " release the lock monitor.").ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return Collections.unmodifiableCollection(this.blockedThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return this.blockedThreadCollection.size();
    }
}
