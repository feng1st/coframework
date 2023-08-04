package io.codeone.framework.chain.logging;

import io.codeone.framework.chain.model.Context;

/**
 * Choose how to log context at two different stages: Before the execution of
 * the chain and before the execution of each node, specified by
 * {@link Context.Builder#withChainLogger(ContextLogger)} and
 * {@link Context.Builder#withNodeLogger(ContextLogger)} respectively.
 *
 * @param <T> The target type of the context.
 */
public interface ContextLogger<T> {

    void log(Context<T> context, Logger logger);
}
