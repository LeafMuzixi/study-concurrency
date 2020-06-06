package com.mzx.concurrency.designPattern.activeObjects.base.impl;

import com.mzx.concurrency.designPattern.activeObjects.base.ActiveObject;
import com.mzx.concurrency.designPattern.activeObjects.base.Result;
import com.mzx.concurrency.designPattern.activeObjects.base.SchedulerThread;

/**
 * 活动代理对象
 */
public class ActiveObjectProxy<T, R> implements ActiveObject<T, R> {
    private final SchedulerThread<T, R> schedulerThread;

    private final ActiveObject<T, R> servant;

    public ActiveObjectProxy(SchedulerThread<T, R> schedulerThread, ActiveObject<T, R> servant) {
        this.schedulerThread = schedulerThread;
        this.servant = servant;
    }

    @Override
    public Result<R> apply(T t) {
        FutureResult<R> futureResult = new FutureResult<>();
        this.schedulerThread.invoke(new FunctionRequest<>(servant, futureResult, t));
        return futureResult;
    }

    @Override
    public void accept(T t) {
        this.schedulerThread.invoke(new ConsumerRequest<>(servant, t));
    }

    @Override
    public Result<R> get() {
        FutureResult<R> futureResult = new FutureResult<>();
        this.schedulerThread.invoke(new SupplierRequest<>(servant, futureResult));
        return futureResult;
    }
}
