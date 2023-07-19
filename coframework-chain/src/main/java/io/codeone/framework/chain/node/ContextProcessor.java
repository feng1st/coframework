package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

public abstract class ContextProcessor implements Node {

    @Override
    public boolean execute(Context<?> context, Logger logger) {
        return process(context, logger);
    }

    protected abstract boolean process(Context<?> context, Logger logger);
}
