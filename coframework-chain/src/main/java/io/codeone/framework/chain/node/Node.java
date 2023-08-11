package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * Represents an executable unit in a chain (Except the {@link SignNode} which
 * has an empty body).
 */
public interface Node {

    /**
     * Executes this node. Returns {@code true} if you want to break the
     * execution of the chain earlier.
     *
     * @param context the context that includes the target the chain operates
     *                on and returns, the initial input arguments of the chain,
     *                and the input/output arguments of each node
     * @param logger  the logger that can and should log concise key information
     * @return {@code true} if the execution of the chain should be aborted
     * earlier
     */
    boolean execute(Context<?> context, Logger logger);
}
