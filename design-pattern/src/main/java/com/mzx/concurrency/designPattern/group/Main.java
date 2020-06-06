package com.mzx.concurrency.designPattern.group;

import java.util.Arrays;

/**
 * 线程组
 */
public class Main {
    public static void main(String[] args) {
        ThreadGroup threadGroup1 = new ThreadGroup("TG1");
        Thread thread1 = new Thread(threadGroup1, Main::print, "T1");
        thread1.start();
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "TG2");
        Thread thread2 = new Thread(threadGroup2, Main::print, "T2");
        thread2.start();
    }

    private synchronized static void print() {
        System.out.println("---------------" + Thread.currentThread().getName() + "---------------");
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        System.out.println(group.getName());
        System.out.println(group.getParent());
        System.out.println(group.getParent().activeCount());
        Thread[] list = new Thread[group.activeCount()];
        group.enumerate(list);
        System.out.println(Arrays.toString(list));
    }
}
