package io.codeone.framework.chain.composite;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.log.Quiet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface Sequential extends Chainable, Composite, Quiet {

    static Sequential of(Chainable... components) {
        return new PlainSequential(components);
    }

    @Override
    default boolean execute(Context context) {
        for (Chainable component : getComponents()) {
            if (!component.run(context)) {
                // break chain earlier
                return false;
            }
        }
        // continue chain
        return true;
    }

    @RequiredArgsConstructor
    @Getter
    class PlainSequential implements Sequential {

        private final Chainable[] components;
    }
}
