package io.codeone.framework.chain.state;

import io.codeone.framework.chain.node.Node;

import java.util.List;

/**
 * The execution state of a chain.
 */
public interface ChainState {

    /**
     * Returns whether this chain is running.
     */
    boolean isRunning() throws InterruptedException;

    /**
     * Pulls the next batch of nodes that are available to run.
     */
    List<Node> pullNodes();

    /**
     * Finishes executing a node.
     *
     * @param broken Should the execution of the chain be aborted.
     */
    void finishNode(Node node, boolean broken);

    /**
     * Waits for new nodes to become available, or all working nodes are
     * finished.
     */
    void waitNodes() throws InterruptedException;
}
