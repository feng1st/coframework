package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * A chain node that only processes the arguments in the context.
 * <p>
 * It can be used in any chain because it does not concern the type of the
 * target of that chain.
 */
public abstract class ContextProcessor implements Node {

    @Override
    public boolean execute(Context<?> context, Logger logger) {
        return process(context, logger);
    }

    /**
     * Performs an action by reading and writing the arguments of a chain. For
     * example, retrieves an object and saves as an argument. Semantically,
     * this is an output argument of this node, and is an input argument of
     * other nodes that read the argument.
     *
     * @return true if the execution of the chain should be aborted earlier.
     */
    protected abstract boolean process(Context<?> context, Logger logger);
}
