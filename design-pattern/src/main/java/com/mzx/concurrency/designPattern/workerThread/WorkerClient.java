package com.mzx.concurrency.designPattern.workerThread;

/**
 * 类似线程池
 * 处于工作状态的工人，只有有任务，就去执行任务
 */
public class WorkerClient {
    public static void main(String[] args) {
        final Channel channel = new Channel(5);
        channel.startWorker();

        new TransportThread("Alex", channel).start();
        new TransportThread("Jack", channel).start();
        new TransportThread("William", channel).start();
    }
}
