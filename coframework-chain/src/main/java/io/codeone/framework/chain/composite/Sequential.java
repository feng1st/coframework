package io.codeone.framework.chain.composite;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.log.Silent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

public interface Sequential extends Chainable, Composite, Silent {

    static Sequential of(Chainable... components) {
        return new PlainSequential(components);
    }

    @Override
    @SneakyThrows
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
