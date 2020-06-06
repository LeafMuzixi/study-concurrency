package com.mzx.concurrency.designPattern.readWriteLock;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SharedData {
    private final char[] buffer;
    private final ReadWriteLock lock = new ReadWriteLock();

    public SharedData(int size) {
        this.buffer = new char[size];
        Arrays.fill(buffer, '*');
    }

    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            return doRead();
        } finally {
            lock.readUnlock();
        }
    }

    private char[] doRead() {
        char[] newBuffer = new char[buffer.length];
        for (int i = 0; i < newBuffer.length; i++) {
            newBuffer[i] = this.buffer[i];
        }
        slowly(50);
        return newBuffer;
    }

    public void write(char c) throws InterruptedException {
        try {
            lock.writeLock();
            doWrite(c);
        } finally {
            lock.writeUnlock();
        }
    }

    private void doWrite(char c) {
        for (int i = 0; i < this.buffer.length; i++) {
            this.buffer[i] = c;
            slowly(10);
        }
    }

    private void slowly(int time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
        }
    }
}
