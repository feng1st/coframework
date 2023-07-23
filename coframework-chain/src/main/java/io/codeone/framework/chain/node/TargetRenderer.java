package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * A chain node that supplies or updates the target of the chain, or its
 * attributes.
 *
 * @param <T> The type of the target.
 */
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

    /**
     * Supplies or updates the target of the chain, or its attributes.
     *
     * @param target Current target of the chain, could be null if there is
     *               none.
     * @return the new or updated target, which will replace the target in the
     * context.
     */
    protected abstract T render(T target, Context<?> context, Logger logger);
}
