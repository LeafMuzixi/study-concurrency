package com.mzx.concurrency.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PhaserExample3 {
    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        IntStream.range(1, 5).forEach(i -> {
            new Athletes(i, phaser).start();
        });
        new InjuredAthletes(5, phaser).start();
    }

    static class InjuredAthletes extends Thread {
        private final int no;

        private final Phaser phaser;

        InjuredAthletes(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                sport(phaser, no + ": start running.", no + ": end running.");

                sport(phaser, no + ": start bicycle.", no + ": end bicycle.");

                System.out.println("Oh shit,I am injured.I will be exited.");
                phaser.arriveAndDeregister();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Athletes extends Thread {
        private final int no;

        private final Phaser phaser;

        Athletes(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                sport(phaser, no + ": start running.", no + ": end running.");

                sport(phaser, no + ": start bicycle.", no + ": end bicycle.");

                sport(phaser, no + ": start long jump.", no + ": end long jump.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sport(Phaser phaser, String s, String s2) throws InterruptedException {
        System.out.println(s);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
        System.out.println(s2);
        phaser.arriveAndAwaitAdvance();
    }
}
