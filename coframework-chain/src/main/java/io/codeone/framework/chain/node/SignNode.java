package io.codeone.framework.chain.node;

import io.codeone.framework.chain.extension.ChainExtension;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * Sign (Signpost) nodes are non-functional nodes that assist the formation of
 * the graph of a chain, for example, connect other functional nodes.
 * <p>
 * Subclasses of sign nodes should be defined by the provider of the chain, and
 * put at somewhere that is visible to the extenders of the chain, e.g. an SDK,
 * so that {@link ChainExtension#addPath(Path)} can use them as "anchors".
 */
public abstract class SignNode implements Node {

    @Override
    public boolean execute(Context<?> context, Logger logger) {
        return false;
    }
}