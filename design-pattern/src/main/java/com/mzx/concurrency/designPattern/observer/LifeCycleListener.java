package com.mzx.concurrency.designPattern.observer;

public interface LifeCycleListener {
    void onEvent(ObserverRunnable.RunnableEvent event);
}
