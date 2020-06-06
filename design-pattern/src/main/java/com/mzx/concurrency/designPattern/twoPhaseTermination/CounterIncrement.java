package com.mzx.concurrency.designPattern.twoPhaseTermination;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 两阶段终止模式
 */
public class CounterIncrement extends Thread {
    private volatile boolean terminated = false;

    private int counter = 0;

    @Override
    public void run() {
        try {
            while (!terminated) {
                System.out.println(Thread.currentThread().getName() + " " + counter++);
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            this.clean();
        }
    }

    private void clean() {
        System.out.println("Do some clean work for the second phase.Current counter " + counter);
    }

    public void close() {
        this.terminated = true;
        this.interrupt();
    }
}
