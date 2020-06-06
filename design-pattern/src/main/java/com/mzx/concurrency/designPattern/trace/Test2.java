package com.mzx.concurrency.designPattern.trace;

import java.util.Arrays;
import java.util.Optional;

public class Test2 {
    public void test() {
        Arrays.stream(Thread.currentThread().getStackTrace())
                .filter(e -> !e.isNativeMethod())
                .forEach(e -> Optional.of(e.getClassName() + " : " + e.getMethodName() + " : " + e.getLineNumber())
                        .ifPresent(System.out::println));
    }
}
