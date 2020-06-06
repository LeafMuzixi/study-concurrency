package com.mzx.concurrency.designPattern.activeObjects.base;

/**
 * 接收异步消息的主动方法
 */
public interface ActiveObject<T, R> {
    Result<R> apply(T t);

    void accept(T t);

    Result<R> get();
}
