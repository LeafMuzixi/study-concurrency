package com.mzx.concurrency.designPattern.threadLocal;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalSimulator<T> {
    private final Map<Thread, T> storage = new HashMap<>();

    public void set(T t) {
        synchronized (this) {
            storage.put(Thread.currentThread(), t);
        }
    }

    public T get() {
        synchronized (this) {
            T value = storage.get(Thread.currentThread());
            if (value == null) {
                return initialValue();
            }
            return value;
        }
    }

    public void remove() {
        synchronized (this) {
            storage.remove(Thread.currentThread());
        }
    }

    public T initialValue() {
        return null;
    }
}
