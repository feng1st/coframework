package io.codeone.framework.chain;

import io.codeone.framework.chain.node.Node;
import io.codeone.framework.chain.node.NodeFactory;
import io.codeone.framework.chain.spec.ChainSpec;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChainFactory {

    @Resource
    private NodeFactory nodeFactory;

    private final Map<ChainSpec, Chain<?>> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> Chain<T> getChain(ChainSpec chainSpec) {
        return (Chain<T>) cache.computeIfAbsent(chainSpec, k -> {
            List<Node> nodes = nodeFactory.getNodes(chainSpec.getNodeClasses());
            return Chain.of(chainSpec, nodes);
        });
    }
}
