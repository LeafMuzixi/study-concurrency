package com.mzx.concurrency.designPattern.sync;

import java.util.function.Consumer;

public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> task) {
        AsyncFuture<T> asyncFuture = new AsyncFuture<>();
        new Thread(() -> {
            T result = task.call();
            asyncFuture.done(result);
        }).start();
        return asyncFuture;
    }

    public <T> void submit(final FutureTask<T> task, final Consumer<T> consumer) {
//        AsyncFuture<T> asyncFuture = new AsyncFuture<>();
        new Thread(() -> {
            T result = task.call();
//            asyncFuture.done(result);
            consumer.accept(result);
        }).start();
    }
}
