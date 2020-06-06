package com.mzx.concurrency.designPattern.demo1;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ProduceConsumer produceConsumer = new ProduceConsumer();
        Stream.of("P1","P2").forEach(name -> {
            new Thread(name){
                @Override
                public void run() {
                    while (true){
                        produceConsumer.produce();
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });

        Stream.of("C1","C2","C3").forEach(name -> {
            new Thread(name){
                @Override
                public void run() {
                    while (true){
                        produceConsumer.consume();
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });
    }
}
