package com.mzx.concurrency.designPattern.close;

import java.util.concurrent.TimeUnit;

/**
 * 优雅退出
 * 利用守护线程，将任务交给线程的守护线程执行，可以实现即时关闭线程
 */
public class Main {
    public static void main(String[] args) {
        ThreadService threadService = new ThreadService();
        long start = System.currentTimeMillis();

        threadService.execute(() -> {
//            while (true) ;
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadService.close(1000 * 10L);

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
