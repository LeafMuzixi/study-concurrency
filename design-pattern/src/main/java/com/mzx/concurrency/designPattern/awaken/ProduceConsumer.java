package com.mzx.concurrency.designPattern.awaken;

public class ProduceConsumer {
    private int i = 0;
    final private Object LOCK = new Object();
    private volatile boolean isProduced = false;

    public void produce() {
        synchronized (LOCK) {
            while (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println("P -> " + i);
            LOCK.notifyAll();
            isProduced = true;
        }
    }

    public void consume() {
        synchronized (LOCK) {
            while (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("C -> " + i);
            isProduced = false;
            LOCK.notifyAll();
        }
    }
}
