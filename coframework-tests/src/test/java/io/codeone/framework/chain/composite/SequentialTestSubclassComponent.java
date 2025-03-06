package io.codeone.framework.chain.composite;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;

public interface SequentialTestSubclassComponent extends Chainable {

    @Override
    default boolean execute(Context context) {
        doStuff(context);
        return true;
    }

    void doStuff(Context context);
}
