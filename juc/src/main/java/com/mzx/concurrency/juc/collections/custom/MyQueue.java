package com.mzx.concurrency.juc.collections.custom;

public class MyQueue<E> {
    private static class Node<E> {
        private E element;

        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return element == null ? "" : element.toString();
        }
    }

    private Node<E> first, last;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E peekFirst() {
        return first == null ? null : first.getElement();
    }

    public E peekLast() {
        return last == null ? null : last.getElement();
    }

    public void addLast(E e) {
        Node<E> node = new Node<>(e, null);
        if (size == 0) {
            this.first = node;
        } else {
            this.last.setNext(node);
        }
        this.last = node;
        this.size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E e = this.first.getElement();
        this.first = this.first.getNext();
        this.size--;
        if (this.size == 0) {
            this.last = null;
        }
        return e;
    }

    public static void main(String[] args) {
        MyQueue<String> queue = new MyQueue<>();
        queue.addLast("Hello");
        queue.addLast("World");
        queue.addLast("Java");
        System.out.println(queue.removeFirst());
        System.out.println(queue.removeFirst());
        System.out.println(queue.removeFirst());
    }
}
