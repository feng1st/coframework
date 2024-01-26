package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;

public interface Empty extends Chainable {

    @Override
    default boolean execute(Context context) {
        // continue chain
        return true;
    }

    @Override
    default boolean run(Context context) {
        // continue chain
        return true;
    }
}
