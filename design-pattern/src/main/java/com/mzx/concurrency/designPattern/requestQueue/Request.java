package com.mzx.concurrency.designPattern.requestQueue;

public class Request {
    private String value;

    public Request(String sendValue) {
        this.value = sendValue;
    }

    public String getValue() {
        return value;
    }
}
