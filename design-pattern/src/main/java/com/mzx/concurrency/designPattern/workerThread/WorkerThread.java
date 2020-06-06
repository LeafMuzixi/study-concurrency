package com.mzx.concurrency.designPattern.workerThread;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class WorkerThread extends Thread {
    private final Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.channel.take().execute();
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }
}
