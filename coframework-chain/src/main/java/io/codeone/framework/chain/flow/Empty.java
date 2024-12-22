package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import lombok.NoArgsConstructor;

/**
 * Represents a chainable unit that performs no operation.
 *
 * <p>This unit always returns {@code true} and continues the chain without any
 * side effects.
 */
@NoArgsConstructor(staticName = "of")
public class Empty implements Chainable {

    /**
     * Executes the chainable unit. Always returns {@code true}.
     *
     * @param context the context in which execution occurs
     * @return {@code true}, always
     */
    @Override
    public boolean execute(Context context) {
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
    public boolean run(Context context) {
        // Continue chain
        return true;
    }
}
