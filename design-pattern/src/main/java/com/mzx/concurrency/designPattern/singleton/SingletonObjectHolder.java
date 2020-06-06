package com.mzx.concurrency.designPattern.singleton;

public class SingletonObjectHolder {
    private SingletonObjectHolder() {
    }

    private static class InstanceHolder {
        private final static SingletonObjectHolder instance = new SingletonObjectHolder();
    }

    public static SingletonObjectHolder getInstance() {
        return InstanceHolder.instance;
    }
}
