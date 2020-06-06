package com.mzx.concurrency.designPattern.gate;

public class Client {
    public static void main(String[] args) {
        Gate gate = new Gate();
        User bj = new User("Baobao", "Beijing", gate);
        User sh = new User("Shanghai", "Shanghai", gate);
        User gz = new User("Guangzhou", "Guangzhou", gate);
        bj.start();
        sh.start();
        gz.start();
    }
}
