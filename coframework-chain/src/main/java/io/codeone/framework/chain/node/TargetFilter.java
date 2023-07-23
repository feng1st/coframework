package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

import java.util.List;

/**
 * A particular node that filter out undesired elements from a list.
 *
 * @param <T> The type of the list element.
 */
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

    /**
     * Filters out unwanted elements.
     *
     * @param target The list that is being filtered.
     * @return The list with remaining elements.
     */
    protected abstract List<T> filter(List<T> target, Context<?> context, Logger logger);
}
