package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;

/**
 * Represents a chainable unit that executes its logic and always continues the
 * chain.
 */
public interface Continuous extends Chainable {

    /**
     * Executes the main logic of this chainable unit and continues the chain.
     *
     * @param context the context in which execution occurs
     * @return {@code true}, always
     */
    @Override
    default boolean execute(Context context) {
        executeAndContinue(context);
        // Continue chain
        return true;
    }

    /**
     * Defines the logic to be executed. This method should not affect the chain's
     * continuation.
     *
     * @param context the context in which execution occurs
     */
    void executeAndContinue(Context context);
}
