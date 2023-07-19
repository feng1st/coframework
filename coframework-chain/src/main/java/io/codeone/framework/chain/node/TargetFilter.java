package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

import java.util.List;

public abstract class TargetFilter<T> implements Node {

    @Override
    @SuppressWarnings("unchecked")
    public boolean execute(Context<?> context, Logger logger) {
        return doExecute((Context<List<T>>) context, logger);
    }

    private boolean doExecute(Context<List<T>> context, Logger logger) {
        if (context.getTarget() == null
                || context.getTarget().isEmpty()) {
            return true;
        }

        context.setTarget(filter(context.getTarget(), context, logger));

        return context.getTarget() == null
                || context.getTarget().isEmpty();
    }

    protected abstract List<T> filter(List<T> target, Context<?> context, Logger logger);
}
