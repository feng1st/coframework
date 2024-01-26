package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;

public interface Continuous extends Chainable {

    @Override
    default boolean execute(Context context) {
        executeAndContinue(context);
        // continue chain
        return true;
    }

    void executeAndContinue(Context context);
}
