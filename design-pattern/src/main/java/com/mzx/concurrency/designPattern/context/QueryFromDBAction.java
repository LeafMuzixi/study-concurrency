package com.mzx.concurrency.designPattern.context;

import java.util.concurrent.TimeUnit;

public class QueryFromDBAction {
    public void execute() {
        try {
            TimeUnit.SECONDS.sleep(1);
            String name = "Alex " + Thread.currentThread().getName();
            ActionContext.getActionContext().getContext().setName(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
