package com.mzx.concurrency.designPattern.workerThread;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Channel {
    public static final int MAX_REQUEST = 100;

    private final Request[] requestQueue;

    private int head;

    private int tail;

    private int count;

    private final WorkerThread[] workerPool;

    public Channel(int workers) {
        this.requestQueue = new Request[MAX_REQUEST];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        this.workerPool = new WorkerThread[workers];
        this.init();
    }

    private void init() {
        IntStream.range(0, this.workerPool.length).forEach(value -> {
            this.workerPool[value] = new WorkerThread("Worker-" + value, this);
        });
    }

    /**
     * push switch to start all of worker to work.
     */
    public void startWorker() {
        Stream.of(workerPool).forEach(WorkerThread::start);
    }

    public synchronized void put(Request request) {
        while (count >= requestQueue.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
        this.requestQueue[tail] = request;
        this.tail = (tail + 1) % requestQueue.length;
        this.count++;
        this.notifyAll();
    }

    public synchronized Request take() {
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
        Request request = this.requestQueue[this.head];
        this.head = (this.head + 1) % this.requestQueue.length;
        this.count--;
        this.notifyAll();
        return request;
    }
}
