package com.mzx.concurrency.juc.collections;

public class PriorityLinkedList<T extends Comparable<T>> {
    private Node<T> first;

    private static final String PLAIN_NULL = "null";

    private int size;

    public PriorityLinkedList() {
        this.first = null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static <T extends Comparable<T>> PriorityLinkedList<T> of(T... elements) {
        final PriorityLinkedList<T> list = new PriorityLinkedList<>();
        if (elements.length > 0) {
            for (T t : elements) {
                list.addFirst(t);
            }
        }
        return list;
    }

    public PriorityLinkedList<T> addFirst(T t) {
        final Node<T> node = new Node<>(t, null);
        Node<T> previous = null;
        Node<T> current = this.first;
        while (current != null && t.compareTo(current.value) > 0) {
            previous = current;
            current = current.next;
        }
        if (previous == null) {
            this.first = node;
        } else {
            previous.next = node;
        }
        node.next = current;
        this.size++;
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
        PriorityLinkedList<Integer> list = PriorityLinkedList.of(10, 1, -10, 2, 9, -4, 6);
        System.out.println(list);
    }
}
