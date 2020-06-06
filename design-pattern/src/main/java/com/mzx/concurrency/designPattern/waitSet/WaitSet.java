package com.mzx.concurrency.designPattern.waitSet;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 1、所有的对象都会有一个 wait set，用来存放调用了该对象 wait 方法之后进入 block 状态的线程
 * 2、线程被 notify 之后，不一定立即得到执行
 * 3、线程从 wait set 中被唤醒的顺序不一定是 FIFO
 * 4、线程被唤醒后，必须重新获取锁
 */
public class WaitSet {
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        // 唤醒顺序并不是等待顺序
        IntStream.rangeClosed(1, 10).forEach(value -> new Thread(() -> {
            synchronized (LOCK) {
                try {
                    Optional.of(Thread.currentThread().getName() + " will come to wait set.").ifPresent(System.out::println);
                    LOCK.wait();
                    Optional.of(Thread.currentThread().getName() + " will leave to wait set.").ifPresent(System.out::println);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, String.valueOf(value)).start());

        TimeUnit.SECONDS.sleep(3);

        IntStream.rangeClosed(1, 10).forEach(value -> {
            synchronized (LOCK) {
                LOCK.notify();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
