package com.mzx.concurrency.designPattern.observer;

public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Binary String: " + Integer.toOctalString(subject.getState()));
    }
}
