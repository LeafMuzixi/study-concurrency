package com.mzx.concurrency.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PhaserExample2 {
    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        IntStream.range(1, 6).forEach(i -> {
            new Athletes(i, phaser).start();
        });
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
                System.out.println(no + ": start running");
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
                System.out.println(no + ": end running");
                phaser.arriveAndAwaitAdvance();

                System.out.println("getPhase => " + phaser.getPhase());

                System.out.println(no + ": start bicycle.");
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
                System.out.println(no + ": end bicycle");
                phaser.arriveAndAwaitAdvance();

                System.out.println("getPhase => " + phaser.getPhase());

                System.out.println(no + ": start long jump.");
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
                System.out.println(no + ": end long jump");
                phaser.arriveAndAwaitAdvance();

                System.out.println("getPhase => " + phaser.getPhase());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
