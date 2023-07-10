package io.codeone.framework.chain;

import java.util.List;

public interface ChainSpec {

    List<Node> getNodes(NodeFactory nodeFactory);
}
