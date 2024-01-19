package io.codeone.framework.chain.state;

import io.codeone.framework.chain.Node;
import io.codeone.framework.chain.graph.Graph;
import io.codeone.framework.chain.node.ExitCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AsyncChainState implements ChainState {

    private final Graph<Node> nodeGraph;

    private final Set<Node> queued = new HashSet<>();

    private final Set<Node> working = new HashSet<>();

    private final Set<Node> finished = new HashSet<>();

    private ExitCode exitCode = null;

    public static AsyncChainState of(Graph<Node> nodeGraph) {
        return new AsyncChainState(nodeGraph);
    }

    private AsyncChainState(Graph<Node> nodeGraph) {
        this.nodeGraph = nodeGraph;

        this.queued.addAll(nodeGraph.getHeadVertices());
    }

    @Override
    public synchronized boolean isRunning() throws InterruptedException {
        if (exitCode != null) {
            while (!working.isEmpty()) {
                wait();
            }
            return false;
        }
        return !queued.isEmpty();
    }

    @Override
    public synchronized List<Node> pullNodes() {
        final List<Node> nodes = new ArrayList<>(queued);
        queued.clear();
        working.addAll(nodes);
        return nodes;
    }

    @Override
    public synchronized void finishNode(Node node, ExitCode exitCode) {
        working.remove(node);
        finished.add(node);

        if (exitCode != null) {
            this.exitCode = exitCode;
            notify();
            return;
        }

        Set<Node> nodes = nodeGraph.getNextVertices(node);
        if (nodes != null && !nodes.isEmpty()) {
            nodes.stream()
                    .filter(o -> finished.containsAll(nodeGraph.getPreviousVertices(o)))
                    .forEach(queued::add);
        }

        notify();
    }

    @Override
    public synchronized void waitNodes() throws InterruptedException {
        while (queued.isEmpty()
                && !working.isEmpty()) {
            wait();
        }
    }
}
