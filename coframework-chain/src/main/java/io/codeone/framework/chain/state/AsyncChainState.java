package io.codeone.framework.chain.state;

import io.codeone.framework.chain.graph.Graph;
import io.codeone.framework.chain.node.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The execution state of a chain, in an asynchronous manner.
 */
public class AsyncChainState implements ChainState {

    private final Graph<Node> nodeGraph;

    private final Set<Node> queued = new HashSet<>();

    private final Set<Node> working = new HashSet<>();

    private final Set<Node> finished = new HashSet<>();

    private boolean broken = false;

    public static AsyncChainState of(Graph<Node> nodeGraph) {
        return new AsyncChainState(nodeGraph);
    }

    private AsyncChainState(Graph<Node> nodeGraph) {
        this.nodeGraph = nodeGraph;

        this.queued.addAll(nodeGraph.getStartingVertices());
    }

    public synchronized boolean isRunning() throws InterruptedException {
        if (broken) {
            // Finishes halfway-done nodes.
            while (!working.isEmpty()) {
                wait();
            }
            return false;
        }
        return !queued.isEmpty();
    }

    public synchronized List<Node> pullNodes() {
        final List<Node> nodes = new ArrayList<>(queued);
        queued.clear();
        working.addAll(nodes);
        return nodes;
    }

    public synchronized void finishNode(Node node, boolean broken) {
        working.remove(node);
        finished.add(node);

        Set<Node> nodes = nodeGraph.getNextVertices(node);
        if (nodes != null && !nodes.isEmpty()) {
            nodes.stream()
                    .filter(o -> finished.containsAll(nodeGraph.getPreviousVertices(o)))
                    .forEach(queued::add);
        }

        if (broken) {
            this.broken = true;
        }

        notify();
    }

    public synchronized void waitNodes() throws InterruptedException {
        while (queued.isEmpty()
                && !working.isEmpty()) {
            wait();
        }
    }
}
