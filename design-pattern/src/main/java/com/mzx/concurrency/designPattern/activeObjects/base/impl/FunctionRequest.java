package com.mzx.concurrency.designPattern.activeObjects.base.impl;

import com.mzx.concurrency.designPattern.activeObjects.base.ActiveObject;
import com.mzx.concurrency.designPattern.activeObjects.base.MethodRequest;
import com.mzx.concurrency.designPattern.activeObjects.base.Result;

public class FunctionRequest<T, R> extends MethodRequest<T, R> {
    private final FutureResult<R> futureResult;

    private final T data;

    public FunctionRequest(ActiveObject<T, R> servant, FutureResult<R> futureResult, T data) {
        super(servant);
        this.futureResult = futureResult;
        this.data = data;
    }

    @Override
    public void execute() {
        Result<R> result = this.servant.apply(data);
        this.futureResult.setResult(result);
    }
}
