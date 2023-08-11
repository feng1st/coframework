package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * A chain node that only processes the arguments in the context.
 *
 * <p>It can be used in any chain because it does not depend on the target type
 * of that chain.
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
     * other nodes that read the argument. Returns {@code true} if you want to
     * break the execution of the chain earlier.
     *
     * @param context the context that includes the target the chain operates
     *                on and returns, the initial input arguments of the chain,
     *                and the input/output arguments of each node
     * @param logger  the logger that can and should log concise key information
     * @return {@code true} if the execution of the chain should be aborted
     * earlier
     */
    protected boolean process(Context<?> context, Logger logger) {
        processAndContinue(context, logger);
        return false;
    }

    /**
     * The same as {@link #process(Context, Logger)} except this method will
     * never break the execution of the chain.
     *
     * @param context the context that includes the target the chain operates
     *                on and returns, the initial input arguments of the chain,
     *                and the input/output arguments of each node
     * @param logger  the logger that can and should log concise key information
     */
    protected void processAndContinue(Context<?> context, Logger logger) {
    }
}
