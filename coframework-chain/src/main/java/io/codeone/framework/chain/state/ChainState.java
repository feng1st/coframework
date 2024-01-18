package io.codeone.framework.chain.state;

import io.codeone.framework.chain.Node;
import io.codeone.framework.chain.node.ExitCode;

import java.util.List;

public interface ChainState {

    boolean isRunning() throws InterruptedException;

    List<Node> pullNodes();

    void finishNode(Node node, ExitCode exitCode);

    void waitNodes() throws InterruptedException;
}
