package com.mzx.concurrency.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {
    private static AtomicStampedReference<Integer> reference = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                boolean success = reference.compareAndSet(100, 101, reference.getStamp(), reference.getStamp() + 1);
                System.out.println(success);
                success = reference.compareAndSet(101, 100, reference.getStamp(), reference.getStamp() + 1);
                System.out.println(success);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                int stamp = reference.getStamp();
                System.out.println("Before sleep: stamp = " + stamp);
                TimeUnit.SECONDS.sleep(2);
                boolean success = reference.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println(success);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
