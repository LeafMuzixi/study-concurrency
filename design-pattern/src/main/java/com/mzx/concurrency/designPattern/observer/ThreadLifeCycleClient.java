package com.mzx.concurrency.designPattern.observer;

import java.util.Arrays;

public class ThreadLifeCycleClient {
    public static void main(String[] args) {
        new ThreadLifeCycleObserver().concurrencyQuery(Arrays.asList("1", "2"));
    }
}
