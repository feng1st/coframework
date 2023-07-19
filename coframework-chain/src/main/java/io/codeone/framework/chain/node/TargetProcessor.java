package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

public abstract class TargetProcessor<T> implements Node {

    @Override
    @SuppressWarnings("unchecked")
    public boolean execute(Context<?> context, Logger logger) {
        return doExecute((Context<T>) context, logger);
    }

    private boolean doExecute(Context<T> context, Logger logger) {
        return process(context.getTarget(), context, logger);
    }

    protected abstract boolean process(T target, Context<?> context, Logger logger);
}
