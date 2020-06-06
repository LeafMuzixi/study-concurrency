package com.mzx.concurrency.designPattern.immutable;

import java.util.stream.IntStream;

public class ImmutableClient {
    public static void main(String[] args) {
        Person person = new Person("Alex", "GuangZhou");
        IntStream.range(0, 10).forEach(value -> new UsePersonThread(person).start());
    }
}
