package io.codeone.framework.chain.chainable;

import io.codeone.framework.chain.Node;

import java.util.Arrays;

public final class Paralleled extends Chainable {

    @SafeVarargs
    public static Paralleled of(Class<? extends Node> first,
                                Class<? extends Node>... rest) {
        return new Paralleled(first, rest);
    }

    public static Paralleled of(Chainable first, Chainable... rest) {
        return new Paralleled(first, rest);
    }

    @SafeVarargs
    private Paralleled(Class<? extends Node> first,
                       Class<? extends Node>... rest) {
        graph.addVertex(first);
        Arrays.stream(rest).forEach(graph::addVertex);
    }

    private Paralleled(Chainable first, Chainable... rest) {
        graph.merge(first.graph);
        Arrays.stream(rest).forEach(chainable
                -> graph.merge(chainable.graph));
    }
}
