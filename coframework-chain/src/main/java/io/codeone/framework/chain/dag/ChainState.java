package io.codeone.framework.chain.dag;

import io.codeone.framework.chain.node.Node;

import java.util.*;

public class ChainState {

    private final Dag<Node> nodeDag;

    private final Set<Node> queued = Collections.newSetFromMap(new IdentityHashMap<>());

    private final Set<Node> working = Collections.newSetFromMap(new IdentityHashMap<>());

    private final Set<Node> finished = Collections.newSetFromMap(new IdentityHashMap<>());

    private boolean broken = false;

    public static ChainState of(Dag<Node> nodeDag) {
        return new ChainState(nodeDag);
    }

    private ChainState(Dag<Node> nodeDag) {
        this.nodeDag = nodeDag;

        this.queued.addAll(nodeDag.getStartingVertices());
    }

    public synchronized boolean isRunning() throws InterruptedException {
        if (broken) {
            waitNodes();
            return false;
        }
        return !queued.isEmpty()
                || !working.isEmpty();
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

        Set<Node> nodes = nodeDag.getNextVertices(node);
        if (nodes != null && !nodes.isEmpty()) {
            nodes.stream()
                    .filter(o -> finished.containsAll(nodeDag.getPreviousVertices(o)))
                    .forEach(queued::add);
        }

        if (broken) {
            this.broken = true;
        }

        notify();
    }

    public synchronized void waitNodes() throws InterruptedException {
        if (!working.isEmpty()) {
            wait();
        }
    }
}
