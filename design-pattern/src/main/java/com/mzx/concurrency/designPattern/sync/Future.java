package com.mzx.concurrency.designPattern.sync;

public interface Future<T> {
    T get() throws InterruptedException;
}
