package com.mzx.concurrency.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PhaserExample6 {
    // awaitAdvance

    /**
     * awaitAdvance can decremental the arrived parties?
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
//        final Phaser phaser = new Phaser(6);
//        new Thread(() -> phaser.awaitAdvance(phaser.getPhase())).start();
//        TimeUnit.SECONDS.sleep(3);
//        System.out.println(phaser.getArrivedParties());

//        phaser.awaitAdvance(phaser.getPhase());
//        System.out.println("====================");

        final Phaser phaser = new Phaser(7);
        IntStream.range(0, 6).boxed().map(i -> phaser).forEach(AwaitAdvanceTask::new);
        phaser.awaitAdvance(phaser.getPhase());
        System.out.println("======================");
    }

    static class AwaitAdvanceTask extends Thread {
        private final Phaser phaser;

        AwaitAdvanceTask(Phaser phaser) {
            this.phaser = phaser;
            start();
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName() + " finished work.");
        }
    }
}
