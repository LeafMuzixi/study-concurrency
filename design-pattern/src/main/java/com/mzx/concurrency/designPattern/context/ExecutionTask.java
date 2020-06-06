package com.mzx.concurrency.designPattern.context;

public class ExecutionTask extends Thread {
    private final QueryFromDBAction queryAction = new QueryFromDBAction();

    private final QueryFromHttpAction httpAction = new QueryFromHttpAction();

    @Override
    public void run() {
        queryAction.execute();
        System.out.println("The name query successful.");
        httpAction.execute();
        System.out.println("The cardId query successful");
        Context context = ActionContext.getActionContext().getContext();
        System.out.println("The name is " + context.getName() + " and cardId is " + context.getCardId());
    }
}
