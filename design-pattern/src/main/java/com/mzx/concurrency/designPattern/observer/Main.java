package com.mzx.concurrency.designPattern.observer;

public class Main {
    public static void main(String[] args) {
        final Subject subject = new Subject();

        new BinaryObserver(subject);
        new OctalObserver(subject);

        System.out.println("======================");
        subject.setState(10);
        System.out.println("======================");
        subject.setState(14);
        System.out.println("======================");
        subject.setState(26);
    }
}
