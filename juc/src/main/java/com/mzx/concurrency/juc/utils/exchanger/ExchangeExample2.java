package com.mzx.concurrency.juc.utils.exchanger;

import java.util.concurrent.Exchanger;

public class ExchangeExample2 {
    public static void main(String[] args) {
        final Exchanger<Object> exchanger = new Exchanger<>();
        new Thread(() -> {
            Object aObj = new Object();
            System.out.println("A will send the object " + aObj);
            try {
                Object exchange = exchanger.exchange(aObj);
                System.out.println("A received the object " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            Object aObj = new Object();
            System.out.println("B will send the object " + aObj);
            try {
                Object exchange = exchanger.exchange(aObj);
                System.out.println("B received the object " + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
