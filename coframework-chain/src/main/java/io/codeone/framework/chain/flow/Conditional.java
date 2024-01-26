package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.log.Silent;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

public interface Conditional extends Chainable, Predicate<Context> {

    static Conditional of(Predicate<Context> predicate, Chainable trueComponent, Chainable falseComponent) {
        return new PlainConditional(predicate, trueComponent, falseComponent);
    }

    static Conditional of(Predicate<Context> predicate, Chainable trueComponent) {
        return new PlainConditional(predicate, trueComponent, null);
    }

    @Override
    default boolean execute(Context context) {
        if (test(context)) {
            return executeIfTrue(context);
        } else {
            return executeIfFalse(context);
        }
    }

    default boolean executeIfTrue(Context context) {
        executeIfTrueAndContinue(context);
        // continue chain
        return true;
    }

    default void executeIfTrueAndContinue(Context context) {
    }

    default boolean executeIfFalse(Context context) {
        executeIfFalseAndContinue(context);
        // continue chain
        return true;
    }

    default void executeIfFalseAndContinue(Context context) {
    }

    @RequiredArgsConstructor
    class PlainConditional implements Conditional, Silent {

        private final Predicate<Context> predicate;

        private final Chainable trueComponent;

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
                // continue chain
                return true;
            }
            return falseComponent.run(context);
        }
    }
}
