package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

import java.util.List;

/**
 * A particular node that filter out undesired elements from a list.
 *
 * @param <T> the type of the list element
 */
public abstract class TargetFilter<T> implements Node {

    @Override
    @SuppressWarnings("unchecked")
    public boolean execute(Context<?> context, Logger logger) {
        return doExecute((Context<List<T>>) context, logger);
    }

    private boolean doExecute(Context<List<T>> context, Logger logger) {
        if (context.getTarget() != null
                && !context.getTarget().isEmpty()) {
            context.setTarget(filter(context.getTarget(), context, logger));
        }
        return false;
    }

    /**
     * Filters out unwanted elements. This method will not be invoked if current
     * target is {@code null} or empty. It is permitted to return {@code null}
     * but an empty list is more recommended in such case.
     *
     * @param target  The list that is being filtered
     * @param context the context that includes the target the chain operates
     *                on and returns, the initial input arguments of the chain,
     *                and the input/output arguments of each node
     * @param logger  the logger that can and should log concise key information
     * @return The list with remaining elements ({@code null} is permitted.)
     */
    protected abstract List<T> filter(List<T> target, Context<?> context, Logger logger);
}
