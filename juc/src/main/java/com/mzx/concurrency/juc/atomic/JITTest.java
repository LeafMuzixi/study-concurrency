package com.mzx.concurrency.juc.atomic;

import java.util.concurrent.TimeUnit;

public class JITTest {
    private static boolean close = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (!close) {
//                System.out.println(".");
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            System.out.println("change");
            close = true;

        }).start();
    }
}
