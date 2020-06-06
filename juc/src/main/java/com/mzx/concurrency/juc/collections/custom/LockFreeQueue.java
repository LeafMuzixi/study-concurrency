package com.mzx.concurrency.juc.collections.custom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class LockFreeQueue<E> {

    private final AtomicReference<Node<E>> head, tail;

    private final AtomicInteger size = new AtomicInteger(0);

    public LockFreeQueue() {
        Node<E> node = new Node<>(null);
        this.head = new AtomicReference<>(node);
        this.tail = new AtomicReference<>(node);
    }

    public boolean isEmpty() {
        return size.get() == 0;
    }

    public void addLast(E e) {
        if (e == null) {
            throw new NullPointerException("the null element not allow.");
        }
        Node<E> node = new Node<>(e);
        Node<E> previousNode = tail.getAndSet(node);
        previousNode.next = node;
        size.incrementAndGet();
    }

    public E removeFirst() {
        if (size.get() <= 0) {
            throw new NullPointerException("empty");
        }
        Node<E> headNode, valueNode;
        do {
            headNode = head.get();
            valueNode = headNode.next;
        } while (valueNode != null && !head.compareAndSet(headNode, valueNode));
        E value = valueNode != null ? valueNode.element : null;
        if (valueNode != null) {
            valueNode.element = null;
        }
        size.decrementAndGet();
        return value;
    }

    private static class Node<E> {
        E element;
        volatile Node<E> next;

        public Node(E element) {
            this.element = element;
        }

        @Override
        public String toString() {
            return element == null ? "" : element.toString();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final LockFreeQueue<Long> queue = new LockFreeQueue<>();
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0, 5).boxed().map(i -> (Runnable) () -> {
            while (counter.getAndIncrement() <= 100) {
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.addLast(System.currentTimeMillis());
            }
        }).forEach(service::submit);
        TimeUnit.MILLISECONDS.sleep(10);
        IntStream.range(0, 5).boxed().map(i -> (Runnable) () -> {
            while (!queue.isEmpty()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(queue.removeFirst());
            }
        }).forEach(service::submit);
        service.shutdown();
    }
}
