package io.codeone.framework.chain.chainable;

import io.codeone.framework.chain.Node;
import io.codeone.framework.chain.graph.Edge;

import java.util.Arrays;

public final class Chained extends Chainable {

    @SafeVarargs
    public static Chained of(Class<? extends Node> first,
                             Class<? extends Node>... rest) {
        return new Chained(first, rest);
    }

    public static Chained of(Chainable first, Chainable... rest) {
        return new Chained(first, rest);
    }

    @SafeVarargs
    private Chained(Class<? extends Node> first,
                    Class<? extends Node>... rest) {
        graph.addVertex(first);
        Class<? extends Node> starting = first;
        for (Class<? extends Node> ending : rest) {
            graph.addEdge(Edge.of(starting, ending));
            starting = ending;
        }
    }

    private Chained(Chainable first, Chainable... rest) {
        graph.merge(first.graph);
        Arrays.stream(rest).forEach(chainable
                -> graph.concat(chainable.graph));
    }
}
