package com.mzx.concurrency.designPattern.twoPhaseTermination;

import java.util.concurrent.TimeUnit;

/**
 * 两阶段终止
 */
public class CounterTest {
    public static void main(String[] args) throws InterruptedException {
        CounterIncrement counterIncrement = new CounterIncrement();
        counterIncrement.start();

        TimeUnit.SECONDS.sleep(10);
        counterIncrement.close();
    }
}
