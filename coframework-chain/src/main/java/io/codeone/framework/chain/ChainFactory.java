package io.codeone.framework.chain;

import io.codeone.framework.chain.graph.Graph;
import io.codeone.framework.chain.node.Node;
import io.codeone.framework.chain.node.NodeFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The factory of chains.
 */
@Component
public class ChainFactory {

    @Resource
    private NodeFactory nodeFactory;

    private final Map<ChainSpec, Chain<?>> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> Chain<T> getChain(ChainSpec chainSpec) {
        return (Chain<T>) cache.computeIfAbsent(chainSpec, k -> {
            Graph<Node> nodeGraph = chainSpec.getNodeGraph(nodeFactory);
            return Chain.of(chainSpec, nodeGraph);
        });
    }
}
