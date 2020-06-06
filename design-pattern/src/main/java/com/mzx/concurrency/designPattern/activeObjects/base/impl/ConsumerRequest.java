package com.mzx.concurrency.designPattern.activeObjects.base.impl;

import com.mzx.concurrency.designPattern.activeObjects.base.ActiveObject;
import com.mzx.concurrency.designPattern.activeObjects.base.MethodRequest;

public class ConsumerRequest<T, R> extends MethodRequest<T, R> {
    private final T data;

    public ConsumerRequest(ActiveObject<T, R> servant, T data) {
        super(servant);
        this.data = data;
    }

    @Override
    public void execute() {
        this.servant.accept(data);
    }
}
