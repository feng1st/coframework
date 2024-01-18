package io.codeone.framework.chain.chainable;

import io.codeone.framework.chain.Node;
import io.codeone.framework.chain.graph.Graph;

public abstract class Chainable {

    protected final Graph<Class<? extends Node>> graph = new Graph<>();
}
