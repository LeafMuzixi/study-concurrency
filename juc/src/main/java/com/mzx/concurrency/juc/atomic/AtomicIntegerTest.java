package com.mzx.concurrency.juc.atomic;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    private static final int MAX = 50000;
    //    private static volatile int value = 0;
    private static volatile AtomicInteger value = new AtomicInteger(0);

    private static Set<Integer> set = new CopyOnWriteArraySet<>();

    private static Runnable runnable = () -> {
        int x = 0;
        while (x < MAX) {
//            int tmp = value;
//            value += 1;
            int tmp = value.getAndIncrement();
            set.add(tmp);
            System.out.println(Thread.currentThread().getName() + ": " + tmp);
            // value = value + 1
            // 1.get value from main memory to local memory
            // 2.add 1 => value
            // 3.assign to value to value
            // 4.flush to main memory
            x++;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(runnable, "T1");
        Thread t2 = new Thread(runnable, "T2");
        Thread t3 = new Thread(runnable, "T3");
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(set.size());
    }
}
