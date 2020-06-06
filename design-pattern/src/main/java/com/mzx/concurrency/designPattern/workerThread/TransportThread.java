package com.mzx.concurrency.designPattern.workerThread;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TransportThread extends Thread {
    private final Channel channel;

    public TransportThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while (true) {
                Request request = new Request(getName(), i);
                this.channel.put(request);
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
                i++;
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }
}
