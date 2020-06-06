package com.mzx.concurrency.designPattern.context;

public final class ActionContext {
    private static final ThreadLocal<Context> threadLocal = ThreadLocal.withInitial(Context::new);

    private enum ActionContextEnum {
        INSTANCE;
        private final ActionContext context;

        ActionContextEnum() {
            this.context = new ActionContext();
        }

        public ActionContext getContext() {
            return context;
        }
    }

    public static ActionContext getActionContext() {
        return ActionContextEnum.INSTANCE.getContext();
    }

    public Context getContext() {
        return threadLocal.get();
    }
}
