package com.mzx.concurrency.designPattern.singleton;

import java.util.stream.IntStream;

/**
 * 三种多线程安全的单例方式
 * 双重检测
 * 利用静态变量初始化线程安全
 * 利用枚举线程安全
 */
public class Main {
    public static void main(String[] args) {
        IntStream.range(1, 100).forEach(value -> new Thread(() -> {
//            System.out.println(SingletonObject.getInstance());
//            System.out.println(SingletonObjectEnum.getInstance());
            System.out.println(SingletonObjectHolder.getInstance());
        }, String.valueOf(value)).start());
    }
}
