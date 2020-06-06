package com.mzx.concurrency.designPattern.capture;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 捕获线程内的异常
 */
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                int result = 1 / 0;
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setUncaughtExceptionHandler((t, e) -> {
            Optional.of(t + " -> " + e).ifPresent(System.out::println);
        });
        thread.start();
    }
}
