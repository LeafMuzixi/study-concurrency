package com.mzx.concurrency.designPattern.activeObjects.base;

import com.mzx.concurrency.designPattern.activeObjects.base.impl.ActiveObjectProxy;

public final class ActiveObjectFactory {
    private ActiveObjectFactory() {
    }

    public static <T, R> ActiveObject<T, R> createActiveObject(ActiveObject<T, R> servant) {
        ActivationQueue<T, R> activationQueue = new ActivationQueue<>();
        SchedulerThread<T, R> schedulerThread = new SchedulerThread<>(activationQueue);
        ActiveObjectProxy<T, R> proxy = new ActiveObjectProxy<>(schedulerThread, servant);
        schedulerThread.start();
        return proxy;
    }
}
