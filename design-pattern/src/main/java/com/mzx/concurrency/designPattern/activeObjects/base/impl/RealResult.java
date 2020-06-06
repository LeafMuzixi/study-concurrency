package com.mzx.concurrency.designPattern.activeObjects.base.impl;

import com.mzx.concurrency.designPattern.activeObjects.base.Result;

public class RealResult<T> implements Result<T> {

    private final T resultValue;

    public RealResult(T resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public T getResultValue() {
        return this.resultValue;
    }
}
