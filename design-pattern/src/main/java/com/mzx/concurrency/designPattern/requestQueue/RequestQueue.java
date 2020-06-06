package com.mzx.concurrency.designPattern.requestQueue;

import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue {
    private final Queue<Request> queue = new LinkedList<>();

    public Request getRequest() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            return queue.poll();
        }
    }

    public void putRequest(Request request) {
        synchronized (queue) {
            queue.offer(request);
            queue.notifyAll();
        }
    }
}
