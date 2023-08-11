package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * A chain node that supplies or updates the target of the chain.
 *
 * @param <T> the type of the target
 */
public abstract class TargetRenderer<T> implements Node {

    @Override
    @SuppressWarnings("unchecked")
    public boolean execute(Context<?> context, Logger logger) {
        return doExecute((Context<T>) context, logger);
    }

    private boolean doExecute(Context<T> context, Logger logger) {
        context.setTarget(render(context.getTarget(), context, logger));
        return false;
    }

    /**
     * Supplies or updates the target of the chain. Returns a new or updated
     * target.
     *
     * @param target  current target of the chain, {@code null} if the target is
     *                not existing
     * @param context the context that includes the target the chain operates
     *                on and returns, the initial input arguments of the chain,
     *                and the input/output arguments of each node
     * @param logger  the logger that can and should log concise key information
     * @return the new or updated target, which will replace the target in the
     * context
     */
    protected T render(T target, Context<?> context, Logger logger) {
        if (target != null) {
            renderIfPresent(target, context, logger);
        }
        return target;
    }

    /**
     * Updates the existing target of the chain. This method will not be invoked
     * if the target is not existing.
     *
     * @param target  current target of the chain, not {@code null}
     * @param context the context that includes the target the chain operates
     *                on and returns, the initial input arguments of the chain,
     *                and the input/output arguments of each node
     * @param logger  the logger that can and should log concise key information
     */
    protected void renderIfPresent(T target, Context<?> context, Logger logger) {
    }
}
