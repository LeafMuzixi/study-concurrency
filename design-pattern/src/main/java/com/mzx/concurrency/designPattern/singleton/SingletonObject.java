package com.mzx.concurrency.designPattern.singleton;

public class SingletonObject {
    private static volatile SingletonObject instance;

    private SingletonObject() {
    }

    public static SingletonObject getInstance() {
        if (null == instance) {
            synchronized (SingletonObject.class) {
                if (null == instance) {
                    instance = new SingletonObject();
                }
            }
        }
        return SingletonObject.instance;
    }
}
