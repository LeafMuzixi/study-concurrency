package com.mzx.concurrency.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PhaserExample1 {
    public static void main(String[] args) {
        final Phaser phaser = new Phaser();
        IntStream.range(0, 5).boxed().map(i -> phaser).forEach(Task::new);
        phaser.register();
        phaser.arriveAndAwaitAdvance();
        System.out.println("All of worker finished the task.");
    }

    static class Task extends Thread {
        private final Phaser phaser;

        Task(Phaser phaser) {
            this.phaser = phaser;
            this.phaser.register();
            start();
        }

        @Override
        public void run() {
            System.out.println("The Worker [" + getName() + "] is working...");
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
}
