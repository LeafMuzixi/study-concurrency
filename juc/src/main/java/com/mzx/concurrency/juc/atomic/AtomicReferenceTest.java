package com.mzx.concurrency.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<Simple> reference = new AtomicReference<>(new Simple("Alex", 12));
        System.out.println(reference.get());
        boolean result = reference.compareAndSet(new Simple("sdfs", 22), new Simple("sdfs", 234));
        System.out.println(result);
    }

    static class ObjectWrap<T>{
        private T t;

        public ObjectWrap(T t) {
            this.t = t;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

    static class Simple {
        private String name;
        private int age;

        public Simple(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
