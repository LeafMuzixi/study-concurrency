package com.mzx.concurrency.designPattern.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 测试 Unsafe 类
 */
public class TestUnsafe {
    static final Unsafe UNSAFE;

    /**
     * 记录变量 state 在类 TestUnsafe 中的偏移值
     */
    static final long stateOffset;

    private volatile long state = 0;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
            stateOffset = UNSAFE.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        TestUnsafe testUnsafe = new TestUnsafe();
        Boolean success = UNSAFE.compareAndSwapInt(testUnsafe, stateOffset, 0, 1);
        System.out.println(success);
    }
}
