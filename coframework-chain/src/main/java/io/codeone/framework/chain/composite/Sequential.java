package io.codeone.framework.chain.composite;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.log.Quiet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a chainable unit that executes its components sequentially.
 *
 * <p>If any component in the sequence returns {@code false}, the execution stops,
 * and the chain breaks.
 */
public interface Sequential extends Chainable, Composite, Quiet {

    /**
     * Creates a new {@code Sequential} instance with the specified components.
     *
     * @param components the components to be executed sequentially
     * @return a new {@code Sequential} instance
     */
    static Sequential of(Chainable... components) {
        return new PlainSequential(Arrays.asList(components));
    }

    /**
     * Executes all components in sequence.
     *
     * @param context the context in which execution occurs
     * @return {@code true} if all components succeed; {@code false} otherwise
     */
    @Override
    default boolean execute(Context context) {
        for (Chainable component : getComponents()) {
            if (!component.run(context)) {
                // Break chain early
                return false;
            }
        }
        // Continue chain
        return true;
    }

    /**
     * A plain implementation of {@link Sequential}.
     */
    @RequiredArgsConstructor
    @Getter
    class PlainSequential implements Sequential {

        /**
         * The components to be executed sequentially.
         */
        private final List<Chainable> components;
    }
}
