package com.mzx.concurrency.juc.utils.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExchangerExample1 {
    /**
     * if the pair thread out reached exchange point, the thread will blocked.
     * use the {@link Exchanger} must be pair.@param args
     */
    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start.");
            try {
                String exchange = exchanger.exchange("I am come from T-A.", 10, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + " get value [" + exchange + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
                System.out.println("Time out.");
            }
            System.out.println(Thread.currentThread().getName() + " end.");
        }, "==A==").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start.");
            try {
                TimeUnit.SECONDS.sleep(20);
                String exchange = exchanger.exchange("I am come from T-B.");
                System.out.println(Thread.currentThread().getName() + " get value [" + exchange + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end.");
        }, "==B==").start();
    }
}
