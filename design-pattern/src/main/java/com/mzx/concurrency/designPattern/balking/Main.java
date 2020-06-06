package com.mzx.concurrency.designPattern.balking;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BalkingData data = new BalkingData("a.txt", "hello world");
        UpdateThread updateThread = new UpdateThread("UpdateThread", data);
        updateThread.start();
        SaveThread saveThread1 = new SaveThread("SaveThread1", data);
        saveThread1.start();
        SaveThread saveThread2 = new SaveThread("SaveThread2", data);
        saveThread2.start();
        TimeUnit.SECONDS.sleep(15);
        updateThread.close();
        saveThread1.close();
        saveThread2.close();
    }
}
