package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

import java.util.List;

public abstract class IndividualTargetRenderer<T> extends BatchTargetRenderer<T> {

    @Override
    protected void renderIfPresent(List<T> target, Context<?> context, Logger logger) {
        target.forEach(o -> renderIfPresent(o, context, logger,
                getIndividualContext(context, o), getIndividualLogger(logger, o)));
    }

    protected abstract void renderIfPresent(T target, Context<?> context, Logger logger,
                                            Context<?> individualContext, Logger individualLogger);
}
