package com.mzx.concurrency.designPattern.activeObjects.base;

/**
 * 调度线程
 */
public class SchedulerThread<T, R> extends Thread {
    private final ActivationQueue<T, R> activationQueue;

    public SchedulerThread(ActivationQueue<T, R> activationQueue) {
        this.activationQueue = activationQueue;
    }

    public void invoke(MethodRequest<T, R> methodRequest) {
        this.activationQueue.put(methodRequest);
    }

    @Override
    public void run() {
        while (true) {
            this.activationQueue.take().execute();
        }
    }
}
