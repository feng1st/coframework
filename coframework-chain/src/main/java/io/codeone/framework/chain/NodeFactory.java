package io.codeone.framework.chain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodeFactory {

    public List<Node> getNodes(ChainSpec chainSpec) {
        return chainSpec.getNodes(this);
    }
}
