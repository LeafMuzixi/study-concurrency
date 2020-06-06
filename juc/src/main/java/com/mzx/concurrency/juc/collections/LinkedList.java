package com.mzx.concurrency.juc.collections;

import org.w3c.dom.Node;

public class LinkedList<T> {
    private Node<T> first;

    private static final String PLAIN_NULL = "null";

    private int size;

    public LinkedList() {
        this.first = null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static <T> LinkedList<T> of(T... elements) {
        final LinkedList<T> list = new LinkedList<>();
        if (elements.length > 0) {
            for (T t : elements) {
                list.addFirst(t);
            }
        }
        return list;
    }

    public LinkedList<T> addFirst(T t) {
        final Node<T> node = new Node<>(t, null);
        node.next = this.first;
        this.size++;
        this.first = node;
        return this;
    }

    public boolean contains(T t) {
        Node<T> current = first;
        while (current != null) {
            if (current.value.equals(t)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            throw new NoElementException("The linked list is empty.");
        }
        Node<T> node = this.first;
        this.first = node.next;
        this.size--;
        return node.value;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        } else {
            StringBuilder builder = new StringBuilder("[");
            Node<T> current = this.first;
            while (current != null) {
                builder.append(current.toString()).append(",");
                current = current.next;
            }
            builder.replace(builder.length() - 1, builder.length(), "]");
            return builder.toString();
        }
    }

    static class NoElementException extends RuntimeException {
        public NoElementException(String message) {
            super(message);
        }
    }

    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            if (value != null) {
                return value.toString();
            }
            return PLAIN_NULL;
        }
    }

    public static void main(String[] args) {
        LinkedList<String> list = LinkedList.of("Hello", "World", "Scala", "Java", "Thread");
        list.addFirst("Concurrency").addFirst("Test");
        System.out.println(list.size);
        System.out.println(list.contains("Scala"));
        System.out.println(list);

        while (!list.isEmpty()) {
            System.out.println(list.removeFirst());
        }
        System.out.println(list.size);
        System.out.println(list.isEmpty());
    }
}
