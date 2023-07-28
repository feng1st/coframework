package io.codeone.framework.chain.node;

import io.codeone.framework.chain.extension.Interference;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

/**
 * Virtual nodes are more like waypoints that assist the formation of the graph
 * of a chain, for example, connect other non-virtual nodes, not do not bear
 * any function.
 * <p>
 * Subclasses of virtual nodes can be put at somewhere that is visible to
 * everyone, e.g. an SDK, so that {@link Interference#addPath(Path)} can use
 * them as "anchors".
 */
public abstract class VirtualNode implements Node {

    @Override
    public boolean execute(Context<?> context, Logger logger) {
        return false;
    }
}
