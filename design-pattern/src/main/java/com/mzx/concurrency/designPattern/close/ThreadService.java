package com.mzx.concurrency.designPattern.close;

import java.util.concurrent.TimeUnit;

public class ThreadService {
    private Thread executeThread;

    private boolean finished = false;

    public void execute(Runnable task) {
        this.executeThread = new Thread(() -> {
            Thread runner = new Thread(task);
            runner.setDaemon(true);
            runner.start();
            try {
                runner.join();
                finished = true;
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        });
        executeThread.start();
    }

    public void close(long maxWaitMilliseconds) {
        long currentTime = System.currentTimeMillis();
        while (!finished) {
            if ((System.currentTimeMillis() - currentTime) >= maxWaitMilliseconds) {
                System.out.println("任务超时，强制结束。");
                this.executeThread.interrupt();
                break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("执行线程被中断!");
                break;
            }
        }
        this.finished = false;
    }
}
