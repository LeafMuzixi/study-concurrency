package com.mzx.concurrency.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.IntStream;

public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe testMe = new TestMe();
        IntStream.range(0, 2).forEach(i -> {
            new Thread(() -> {
                final int MAX = 20;
                IntStream.range(0, MAX).forEach(j -> {
                    int value = updater.getAndIncrement(testMe);
                    System.out.println(Thread.currentThread().getName() + " => " + value);
                });
            }).start();
        });
    }

    static class TestMe {
        volatile int i;
    }
}
