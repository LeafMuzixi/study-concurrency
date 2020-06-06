package com.mzx.concurrency.designPattern.activeObjects;

import java.util.Arrays;

public class MakeData {
    private int count;
    private char fillChar;

    public MakeData(int count, char fillChar) {
        this.count = count;
        this.fillChar = fillChar;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public char getFillChar() {
        return fillChar;
    }

    public void setFillChar(char fillChar) {
        this.fillChar = fillChar;
    }

    @Override
    public String toString() {
        char[] chars = new char[count];
        Arrays.fill(chars, fillChar);
        return new String(chars);
    }
}
