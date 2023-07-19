package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

public abstract class TargetRenderer<T> implements Node {

    @Override
    @SuppressWarnings("unchecked")
    public boolean execute(Context<?> context, Logger logger) {
        doExecute((Context<T>) context, logger);
        return false;
    }

    private void doExecute(Context<T> context, Logger logger) {
        context.setTarget(render(context.getTarget(), context, logger));
    }

    protected abstract T render(T target, Context<?> context, Logger logger);
}
