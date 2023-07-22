package io.codeone.framework.chain.state;

import io.codeone.framework.chain.dag.Dag;
import io.codeone.framework.chain.node.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SyncChainState implements ChainState {

    private final Dag<Node> nodeDag;

    private final Set<Node> queued = new HashSet<>();

    private final Set<Node> finished = new HashSet<>();

    public static SyncChainState of(Dag<Node> nodeDag) {
        return new SyncChainState(nodeDag);
    }

    private SyncChainState(Dag<Node> nodeDag) {
        this.nodeDag = nodeDag;

        this.queued.addAll(nodeDag.getStartingVertices());
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

        Set<Node> nodes = nodeDag.getNextVertices(node);
        if (nodes != null && !nodes.isEmpty()) {
            nodes.stream()
                    .filter(o -> finished.containsAll(nodeDag.getPreviousVertices(o)))
                    .forEach(queued::add);
        }
    }

    public void waitNodes() {
    }
}
