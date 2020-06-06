package com.mzx.concurrency.designPattern.activeObjects.base.impl;

import com.mzx.concurrency.designPattern.activeObjects.base.ActiveObject;
import com.mzx.concurrency.designPattern.activeObjects.base.MethodRequest;
import com.mzx.concurrency.designPattern.activeObjects.base.Result;

public class SupplierRequest<T, R> extends MethodRequest<T, R> {
    private final FutureResult<R> futureResult;

    public SupplierRequest(ActiveObject<T, R> servant, FutureResult<R> futureResult) {
        super(servant);
        this.futureResult = futureResult;
    }

    @Override
    public void execute() {
        Result<R> result = this.servant.get();
        this.futureResult.setResult(result);
    }
}
