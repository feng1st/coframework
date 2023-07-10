package io.codeone.framework.chain;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ChainFactory {

    @Resource
    private NodeFactory nodeFactory;

    public Chain getChain(ChainSpec chainSpec) {
        List<Node> nodes = nodeFactory.getNodes(chainSpec);
        return null;
    }
}
