package io.codeone.framework.chain.node;

import io.codeone.framework.chain.extension.ChainDecorator;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * Sign (Signpost) nodes are non-functional nodes that assist the formation of
 * the graph of a chain, for example, connect other functional nodes.
 *
 * <p>Subclasses of sign nodes should be defined by the provider of the chain,
 * and put at somewhere that is visible to the extenders of the chain, such as
 * an SDK, so that {@link ChainDecorator#addPath(Path)} can use them as
 * "anchors".
 */
public abstract class SignNode implements Node {

    /**
     * Does nothing and proceeds to the next node in the chain.
     *
     * @param context the context which is not used here
     * @param logger  the logger which is not used here
     * @return always {@code false}
     */
    @Override
    public boolean execute(Context<?> context, Logger logger) {
        return false;
    }
}
