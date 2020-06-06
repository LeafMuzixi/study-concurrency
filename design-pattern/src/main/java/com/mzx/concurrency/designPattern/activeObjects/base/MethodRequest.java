package com.mzx.concurrency.designPattern.activeObjects.base;

/**
 * 对应 ActiveObject 中的每一个方法
 */
public abstract class MethodRequest<T, R> {
    protected final ActiveObject<T, R> servant;

    protected MethodRequest(ActiveObject<T, R> servant) {
        this.servant = servant;
    }

    public abstract void execute();
}
