package io.codeone.framework.chain.chainable;

import io.codeone.framework.chain.Node;

public final class Single extends Chainable {

    public static Single of(Class<? extends Node> nodeClass) {
        return new Single(nodeClass);
    }

    private Single(Class<? extends Node> nodeClass) {
        graph.addVertex(nodeClass);
    }
}
