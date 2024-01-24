package io.codeone.framework.chain.factory;

import io.codeone.framework.chain.ChainExecutor;
import io.codeone.framework.chain.ChainSpec;
import io.codeone.framework.chain.chain.Chain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChainFactory {

    @Resource
    private NodeFactory nodeFactory;

    private final Map<Integer, Chain> cache = new ConcurrentHashMap<>();

    public ChainFactory() {
        ChainExecutor.registerChainFactory(this);
    }

    public Chain getChain(ChainSpec chainSpec) {
        return cache.computeIfAbsent(chainSpec.getId(), k
                -> Chain.of(chainSpec.toNodeGraph(nodeFactory::getNode)));
    }
}
