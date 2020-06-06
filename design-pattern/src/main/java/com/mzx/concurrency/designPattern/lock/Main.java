package com.mzx.concurrency.designPattern.lock;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 自定义锁
 */
public class Main {
    public static void main(String[] args) {
        final BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1", "T2", "T3", "T4").forEach(name -> new Thread(() -> {
            try {
                booleanLock.lock(100L);
                Optional.of(Thread.currentThread().getName() + " have the lock monitor.")
                        .ifPresent(System.out::println);
                work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Lock.TimeOutException e) {
                Optional.of(Thread.currentThread().getName() + " -> " + e.getMessage()).ifPresent(System.out::println);
            } finally {
                booleanLock.unlock();
            }
        }, name).start());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        booleanLock.unlock();
    }

    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working...").ifPresent(System.out::println);
        TimeUnit.SECONDS.sleep(10);
    }
}
