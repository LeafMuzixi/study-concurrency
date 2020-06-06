package com.mzx.concurrency.designPattern.limit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 该模式会同时启动多个线程，但是同时进行的线程只有 5 个，且只有 5 个线程会操作 Control 资源
 * 假设操作 Control 资源需要耗费大量内存，这种方式可以限制耗费内存的数量
 * 同时，线程运行从 10 个之间切换减少到 5 个之间切换，可以减少线程切换上下文的开销
 */
public class CaptureService {
    private final static LinkedList<Control> CONTROLS = new LinkedList<>();
    private final static int MAX_WORKER = 5;

    public static void main(String[] args) {
        List<Thread> worker = new ArrayList<>();
        Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10")
                .map(CaptureService::createCaptureThread)
                .forEach(thread -> {
                    worker.add(thread);
                    thread.start();
                });
        worker.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("All of capture work finished.");
    }

    private static Thread createCaptureThread(String name) {
        return new Thread(() -> {
            ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
            Control control;
            Optional.of("The worker [" + Thread.currentThread().getName() + "] begin capture data.").ifPresent(System.out::println);
            synchronized (CONTROLS) {
                while (CONTROLS.size() >= MAX_WORKER) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                control = new Control();
                CONTROLS.add(control);
            }
            int sleep = RANDOM.nextInt(10);
            Optional.of("The worker [" + Thread.currentThread().getName() + "] is working... need " + sleep + "seconds.").ifPresent(System.out::println);
            try {
                TimeUnit.SECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (CONTROLS) {
                Optional.of("The worker [" + Thread.currentThread().getName() + "] end capture data.").ifPresent(System.out::println);
                CONTROLS.remove(control);
                CONTROLS.notifyAll();
            }
        }, name);
    }

    private static class Control {

    }
}
