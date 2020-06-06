package com.mzx.concurrency.designPattern.hook;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 程序退出的钩子函数
 */
public class ExitCapture {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("The program will be exit.");
            notifyAndRelease();
        }));
        IntStream.range(1, 100).forEach(value -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("I'm working...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        throw new RuntimeException("error");
    }

    private static void notifyAndRelease() {
        System.out.println("Notify to be the admin.");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Will release resource.");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Release resource done.");
    }
}
