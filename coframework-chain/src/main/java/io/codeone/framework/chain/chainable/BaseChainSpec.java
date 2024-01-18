package io.codeone.framework.chain.chainable;

import io.codeone.framework.chain.Node;
import io.codeone.framework.chain.graph.Graph;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseChainSpec extends Chainable {

    private static final ChainSpecPool SPEC_POOL = new ChainSpecPool();

    @Getter
    private final int id;

    protected BaseChainSpec(Chainable first, Chainable... rest) {
        graph.merge(first.graph);
        Arrays.stream(rest).forEach(chainable
                -> graph.merge(chainable.graph));

        graph.detectCycle().ifPresent(o -> {
            throw new IllegalStateException("A cycle detected in the spec: "
                    + o.stream()
                    .map(Class::getSimpleName)
                    .collect(Collectors.toList()));
        });

        id = SPEC_POOL.getId(graph);
    }

    public Graph<Node> toNodeGraph(
            Function<Class<? extends Node>, Node> toNodeFunction) {
        return graph.transform(toNodeFunction);
    }
}
