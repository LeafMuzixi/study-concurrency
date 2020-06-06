package com.mzx.concurrency.designPattern.context;

import java.util.stream.IntStream;

public class ContextTest {
    public static void main(String[] args) {
        IntStream.range(0, 5).forEach(value -> {
            new ExecutionTask().start();
        });
    }
}
