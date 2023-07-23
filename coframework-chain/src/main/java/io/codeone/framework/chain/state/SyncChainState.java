package io.codeone.framework.chain.state;

import io.codeone.framework.chain.graph.Graph;
import io.codeone.framework.chain.node.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The execution state of a chain, in a synchronous manner.
 */
public class SyncChainState implements ChainState {

    private final Graph<Node> nodeGraph;

    private final Set<Node> queued = new HashSet<>();

    private final Set<Node> finished = new HashSet<>();

    public static SyncChainState of(Graph<Node> nodeGraph) {
        return new SyncChainState(nodeGraph);
    }

    private SyncChainState(Graph<Node> nodeGraph) {
        this.nodeGraph = nodeGraph;

        this.queued.addAll(nodeGraph.getStartingVertices());
    }

    public boolean isRunning() {
        return !queued.isEmpty();
    }

    public List<Node> pullNodes() {
        final List<Node> nodes = new ArrayList<>(queued);
        queued.clear();
        return nodes;
    }

    public void finishNode(Node node, boolean broken) {
        finished.add(node);

        Set<Node> nodes = nodeGraph.getNextVertices(node);
        if (nodes != null && !nodes.isEmpty()) {
            nodes.stream()
                    .filter(o -> finished.containsAll(nodeGraph.getPreviousVertices(o)))
                    .forEach(queued::add);
        }
    }

    public void waitNodes() {
    }
}
