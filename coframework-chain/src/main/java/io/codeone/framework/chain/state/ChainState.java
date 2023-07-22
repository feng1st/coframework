package io.codeone.framework.chain.state;

import io.codeone.framework.chain.node.Node;

import java.util.List;

public interface ChainState {

    boolean isRunning() throws InterruptedException;

    List<Node> pullNodes();

    void finishNode(Node node, boolean broken);

    void waitNodes() throws InterruptedException;
}
