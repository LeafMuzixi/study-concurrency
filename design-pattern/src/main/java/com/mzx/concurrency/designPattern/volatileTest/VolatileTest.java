package com.mzx.concurrency.designPattern.volatileTest;

import java.util.concurrent.TimeUnit;

/**
 * 当没有 volatile 关键字时，READER 并不能感知到 INIT_VALUE 值发生了变化
 *
 * volatile
 * 1、保证重排序，不会把后面的指令放在屏障的前面，也不会把前面的放到后面
 * 2、强制对缓存的修改操作会立即写入内存
 * 3、如果是写操作，会导致其它 cpu 中的缓存失效
 */
public class VolatileTest {
    private static volatile int INIT_VALUE = 0;

    private final static int MAX_LIMIT = 5;

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (localValue < MAX_LIMIT) {
                if (localValue != INIT_VALUE) {
                    System.out.printf("The value updated to [%d]\n", INIT_VALUE);
                    localValue = INIT_VALUE;
                }
            }
        }, "READER").start();
        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (INIT_VALUE < MAX_LIMIT) {
                System.out.printf("Update the value to [%d]\n", ++localValue);
                INIT_VALUE = localValue;
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "UPDATER").start();
    }
}
