package com.mzx.concurrency.designPattern.sync;

import java.util.concurrent.TimeUnit;

/**
 * Future   -> 代表未来的一个凭据
 * FutureTask   -> 将调用逻辑进行隔离
 * FutureService    -> 桥接 Future 和 FutureTask
 */
public class SyncInvoker {
    public static void main(String[] args) throws InterruptedException {
//        String result = get();
//        System.out.println(result);
        FutureService futureService = new FutureService();
        futureService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISH-CALLBACK";
        }, System.out::println);
        Future<String> future = futureService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISH";
        });
        System.out.println("=================");
        System.out.println("do other thing.");
        System.out.println("=================");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(future.get());
    }

    private static String get() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        return "FINISH";
    }
}
