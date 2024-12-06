package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.log.Quiet;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

/**
 * Represents a chainable unit that conditionally executes one of two components
 * based on a predicate.
 */
public interface Conditional extends Chainable, Predicate<Context> {

    /**
     * Creates a conditional chainable unit with true and false branches.
     *
     * @param predicate      the condition to evaluate
     * @param trueComponent  the component to execute if the condition is true
     * @param falseComponent the component to execute if the condition is false
     * @return a new {@code Conditional} instance
     */
    static Conditional of(Predicate<Context> predicate, Chainable trueComponent, Chainable falseComponent) {
        return new PlainConditional(predicate, trueComponent, falseComponent);
    }

    /**
     * Creates a conditional chainable unit with a true branch only.
     *
     * @param predicate     the condition to evaluate
     * @param trueComponent the component to execute if the condition is true
     * @return a new {@code Conditional} instance
     */
    static Conditional of(Predicate<Context> predicate, Chainable trueComponent) {
        return new PlainConditional(predicate, trueComponent, null);
    }

    /**
     * Executes the appropriate component based on the predicate result.
     *
     * @param context the context in which execution occurs
     * @return {@code true} if the executed component succeeded; {@code false} otherwise
     */
    @Override
    default boolean execute(Context context) {
        if (test(context)) {
            return executeIfTrue(context);
        } else {
            return executeIfFalse(context);
        }
    }

    /**
     * Executes the component associated with the true branch.
     *
     * @param context the context in which execution occurs
     * @return {@code true} to continue the chain
     */
    default boolean executeIfTrue(Context context) {
        executeIfTrueAndContinue(context);
        return true;
    }

    /**
     * Executes the true branch logic and continues the chain.
     *
     * @param context the context in which execution occurs
     */
    default void executeIfTrueAndContinue(Context context) {
    }

    /**
     * Executes the component associated with the false branch.
     *
     * @param context the context in which execution occurs
     * @return {@code true} to continue the chain
     */
    default boolean executeIfFalse(Context context) {
        executeIfFalseAndContinue(context);
        return true;
    }

    /**
     * Executes the false branch logic and continues the chain.
     *
     * @param context the context in which execution occurs
     */
    default void executeIfFalseAndContinue(Context context) {
    }

    /**
     * A plain implementation of {@link Conditional}.
     */
    @RequiredArgsConstructor
    class PlainConditional implements Conditional, Quiet {

        /**
         * The condition to evaluate.
         */
        private final Predicate<Context> predicate;

        /**
         * The component to execute if the condition is true.
         */
        private final Chainable trueComponent;

        /**
         * The component to execute if the condition is false.
         */
        private final Chainable falseComponent;

        @Override
        public boolean test(Context context) {
            return predicate.test(context);
        }

        @Override
        public boolean executeIfTrue(Context context) {
            return trueComponent.run(context);
        }

        @Override
        public boolean executeIfFalse(Context context) {
            if (falseComponent == null) {
                // Continue chain if no false branch
                return true;
            }
            return falseComponent.run(context);
        }
    }
}
