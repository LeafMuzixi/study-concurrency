package com.mzx.concurrency.designPattern.activeObjects.base.impl;

import com.mzx.concurrency.designPattern.activeObjects.base.Result;

public class FutureResult<T> implements Result<T> {
    private Result<T> result;

    private boolean ready = false;

    public synchronized void setResult(Result<T> result) {
        this.result = result;
        this.ready = true;
        this.notifyAll();
    }

    @Override
    public synchronized T getResultValue() {
        while (!ready) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.result.getResultValue();
    }
}
