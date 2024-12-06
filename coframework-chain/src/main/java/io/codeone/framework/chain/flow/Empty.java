package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;

/**
 * Represents a chainable unit that performs no operation.
 *
 * <p>This unit always returns {@code true} and continues the chain without any
 * side effects.
 */
public interface Empty extends Chainable {

    /**
     * Executes the chainable unit. Always returns {@code true}.
     *
     * @param context the context in which execution occurs
     * @return {@code true}, always
     */
    @Override
    default boolean execute(Context context) {
        // Continue chain
        return true;
    }

    /**
     * Runs the chainable unit. Always returns {@code true}.
     *
     * @param context the context in which execution occurs
     * @return {@code true}, always
     */
    @Override
    default boolean run(Context context) {
        // Continue chain
        return true;
    }
}
