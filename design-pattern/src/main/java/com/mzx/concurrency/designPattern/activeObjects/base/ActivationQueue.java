package com.mzx.concurrency.designPattern.activeObjects.base;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 请求队列
 * @param <T>
 * @param <R>
 */
public class ActivationQueue<T, R> {
    private static final int MAX_METHOD_REQUEST_QUEUE_SIZE = 100;

    private final Queue<MethodRequest<T, R>> methodRequests;

    public ActivationQueue() {
        this.methodRequests = new LinkedList<>();
    }

    public synchronized void put(MethodRequest<T, R> request) {
        while (this.methodRequests.size() >= MAX_METHOD_REQUEST_QUEUE_SIZE) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.methodRequests.offer(request);
        this.notifyAll();
    }

    public synchronized MethodRequest<T, R> take() {
        while (this.methodRequests.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MethodRequest<T, R> methodRequest = this.methodRequests.poll();
        this.notifyAll();
        return methodRequest;
    }
}
