package com.mzx.concurrency.designPattern.readWriteLock;

public class ReadWriteLockClient {
    public static void main(String[] args) {
        final SharedData sharedData = new SharedData(10);
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new WriteWorker(sharedData, "hdskfhsfksad").start();
        new WriteWorker(sharedData, "pjfosfjoisfj").start();
        new WriteWorker(sharedData, "aofkpaokfpaoa").start();
        new WriteWorker(sharedData, "opkodpfjadfjia").start();
    }
}