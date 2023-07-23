package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * The node of a chain.
 */
public interface Node {

    /**
     * Executes the node.
     *
     * @param context The context that includes the target and the input,
     *                output arguments of each node.
     * @param logger  The logger that can log concise key information.
     * @return true if the execution of the chain should be aborted earlier.
     */
    boolean execute(Context<?> context, Logger logger);
}
