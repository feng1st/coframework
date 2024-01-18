package io.codeone.framework.chain.chainable;

import io.codeone.framework.chain.Node;
import io.codeone.framework.chain.graph.Graph;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

final class ChainSpecPool {

    private final Map<Graph<Class<? extends Node>>, Integer> idMap
            = new ConcurrentHashMap<>();

    private final AtomicInteger idGenerator = new AtomicInteger();

    int getId(Graph<Class<? extends Node>> graph) {
        return idMap.computeIfAbsent(graph,
                k -> idGenerator.getAndIncrement());
    }
}
