package com.mzx.concurrency.designPattern.producerConsumer;


public class ProducerAndConsumerClient {
    public static void main(String[] args) {
        final MessageQueue queue = new MessageQueue();
        ProducerThread producerThread1 = new ProducerThread(queue, 1);
        ProducerThread producerThread2 = new ProducerThread(queue, 2);
        ProducerThread producerThread3 = new ProducerThread(queue, 3);
        producerThread1.start();
        producerThread2.start();
        producerThread3.start();
        ConsumerThread consumerThread1 = new ConsumerThread(queue, 1);
        ConsumerThread consumerThread2 = new ConsumerThread(queue, 2);
        consumerThread1.start();
        consumerThread2.start();
    }
}
