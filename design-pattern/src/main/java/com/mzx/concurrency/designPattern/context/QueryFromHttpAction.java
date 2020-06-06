package com.mzx.concurrency.designPattern.context;

import java.util.concurrent.TimeUnit;

public class QueryFromHttpAction {
    public void execute() {
        Context context = ActionContext.getActionContext().getContext();
        String name = context.getName();
        String cardId = getCardId(name);
        context.setCardId(cardId);
    }

    private String getCardId(String name) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "16153135415643" + Thread.currentThread().getId();
    }
}
