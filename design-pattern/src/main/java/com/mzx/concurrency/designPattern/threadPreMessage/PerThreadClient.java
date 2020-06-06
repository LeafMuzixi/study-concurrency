package com.mzx.concurrency.designPattern.threadPreMessage;

import java.util.stream.IntStream;

/**
 * 并发处理消息
 */
public class PerThreadClient {
    public static void main(String[] args) {
        final MessageHandler messageHandler = new MessageHandler();
        IntStream.range(0, 50).forEach(value -> messageHandler.request(new Message(String.valueOf(value))));
        messageHandler.shutdown();
    }
}
