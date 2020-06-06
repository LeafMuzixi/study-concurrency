package com.mzx.concurrency.designPattern.balking;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class UpdateThread extends Thread {
    private final BalkingData data;

    private boolean flag = false;

    public UpdateThread(@NotNull String name, BalkingData data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; !flag; i++) {
                String content = "No." + i;
                data.change(content);
                System.out.println(Thread.currentThread().getName() + " updated data. " + content);
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(3000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.flag = true;
    }
}
