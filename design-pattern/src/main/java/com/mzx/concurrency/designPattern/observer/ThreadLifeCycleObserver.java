package com.mzx.concurrency.designPattern.observer;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadLifeCycleObserver implements LifeCycleListener {
    private final Object LOCK = new Object();

    public void concurrencyQuery(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.forEach(id -> new Thread(new ObserverRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                    System.out.println("query for the id " + id);
                    TimeUnit.SECONDS.sleep(1);
                    notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                } catch (InterruptedException e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                }
            }
        }, id).start());
    }

    @Override
    public void onEvent(ObserverRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println("The runnable [" + event.getThread().getName() + "] data changed and state is [" + event.getState() + "]");
            if (event.getCause() != null) {
                System.out.println("The runnable [" + event.getThread().getName() + "] process failed.");
                event.getCause().printStackTrace();
            }
        }
    }
}
