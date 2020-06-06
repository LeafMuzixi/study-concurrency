package com.mzx.concurrency.designPattern.awaken;

import java.util.stream.Stream;

/**
 * 使用 notifyAll 解决假唤醒问题
 */
public class Main {
    public static void main(String[] args) {
        ProduceConsumer produceConsumer = new ProduceConsumer();
        Stream.of("P1", "P2").forEach(name -> new Thread(() -> {
            while (true) {
                produceConsumer.produce();
            }
        }).start());
        Stream.of("C1", "C2").forEach(name -> new Thread(() -> {
            while (true) {
                produceConsumer.consume();
            }
        }).start());
    }
}
