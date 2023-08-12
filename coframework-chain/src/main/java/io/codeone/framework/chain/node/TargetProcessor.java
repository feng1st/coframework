package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * A node that do some general operations to the target of the chain
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
        if (context.getTarget() == null) {
            return false;
        }
        return process(context.getTarget(), context, logger);
    }

    /**
     * Manipulates the target of the chain. This method will not be invoked if
     * current target is null. Returns {@code true} if you want to break the
     * execution of the chain earlier.
     *
     * @param target  the target this node operates on
     * @param context the context that includes the target the chain operates
     *                on and returns, the initial input arguments of the chain,
     *                and the input/output arguments of each node
     * @param logger  the logger that can and should log concise key information
     * @return {@code true} if the execution of the chain should be aborted
     * earlier
     */
    protected boolean process(T target, Context<?> context, Logger logger) {
        processAndContinue(target, context, logger);
        return false;
    }

    /**
     * The same as {@link #process(Object, Context, Logger)} except this method
     * will never break the execution of the chain.
     *
     * @param target  the target this node operates on
     * @param context the context that includes the target the chain operates
     *                on and returns, the initial input arguments of the chain,
     *                and the input/output arguments of each node
     * @param logger  the logger that can and should log concise key information
     */
    protected void processAndContinue(T target, Context<?> context, Logger logger) {
    }
}
