package com.mzx.concurrency.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PhaserExample5 {
    // arrive
    public static void main(String[] args) throws InterruptedException {
//        final Phaser phaser = new Phaser(3);
//        new Thread(phaser::arrive).start();
//        TimeUnit.SECONDS.sleep(4);
        final Phaser phaser = new Phaser(5);
        IntStream.range(0, 4).forEach(i -> {
            new ArriveTask(i, phaser).start();
        });
        phaser.arriveAndAwaitAdvance();
        System.out.println("The phaser 1 work finished done.");
    }

    static class ArriveTask extends Thread {
        private final Phaser phaser;

        ArriveTask(int no, Phaser phaser) {
            super(String.valueOf(no));
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(getName() + " start working.");
            PhaserExample5.sleep();
            System.out.println(getName() + " The phaser one is running.");
            phaser.arrive();

            PhaserExample5.sleep();

            System.out.println(getName() + " keep to do other thing.");
        }
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
