package com.mzx.concurrency.juc.executors;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 */
public class TimerScheduler {
    public static void main(String[] args) {
        Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("=====>" + System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }
}
