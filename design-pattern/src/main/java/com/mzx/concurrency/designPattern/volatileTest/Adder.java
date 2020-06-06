package com.mzx.concurrency.designPattern.volatileTest;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Adder {
    private static int INIT_VALUE = 0;

    private final static int MAX_LIMIT = 50;

    public static void main(String[] args) {
        new Thread(() -> {
            while (INIT_VALUE < MAX_LIMIT) {
                Optional.of(Thread.currentThread().getName() + " -> " + (++INIT_VALUE)).ifPresent(System.out::println);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-1").start();
        new Thread(() -> {
            while (INIT_VALUE < MAX_LIMIT) {
                Optional.of(Thread.currentThread().getName() + " -> " + (++INIT_VALUE)).ifPresent(System.out::println);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-2").start();
    }
}
