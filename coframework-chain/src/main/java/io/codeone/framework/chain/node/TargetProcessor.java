package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * A node that do some general operations to the target.
 *
 * @param <T> The type of the target.
 */
public abstract class TargetProcessor<T> implements Node {

    @Override
    @SuppressWarnings("unchecked")
    public boolean execute(Context<?> context, Logger logger) {
        return doExecute((Context<T>) context, logger);
    }

    private boolean doExecute(Context<T> context, Logger logger) {
        return process(context.getTarget(), context, logger);
    }

    /**
     * Manipulates the target of the chain.
     *
     * @param target The target this node operates.
     * @return true if the execution of the chain should be aborted earlier.
     */
    protected abstract boolean process(T target, Context<?> context, Logger logger);
}
