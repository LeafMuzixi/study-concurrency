package com.mzx.concurrency.designPattern.balking;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class SaveThread extends Thread {
    private final BalkingData data;

    private boolean flag = false;

    public SaveThread(@NotNull String name, BalkingData data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (!flag) {
                data.save();
                TimeUnit.MILLISECONDS.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.flag = true;
    }
}
