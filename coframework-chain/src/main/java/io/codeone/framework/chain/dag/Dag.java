package io.codeone.framework.chain.dag;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;

@EqualsAndHashCode
public class Dag<T> {

    private final Map<T, Set<T>> nextMap = new HashMap<>();

    private final Map<T, Set<T>> prevMap = new HashMap<>();

    @Getter
    private final Set<T> startingVertices = new HashSet<>();

    /**
     * Constructs an empty dag.
     */
    public Dag() {
    }

    /**
     * Constructs a dag with only one vertex.
     */
    public Dag(T isolatedVertex) {
        startingVertices.add(isolatedVertex);
    }

    /**
     * Constructs a dag from edges.
     */
    public Dag(List<Edge<T>> edges) {
        for (Edge<T> edge : edges) {
            nextMap.computeIfAbsent(edge.getFrom(), k -> new HashSet<>())
                    .add(edge.getTo());
            prevMap.computeIfAbsent(edge.getTo(), k -> new HashSet<>())
                    .add(edge.getFrom());
        }
        startingVertices.addAll(nextMap.keySet());
        startingVertices.removeAll(prevMap.keySet());
    }

    /**
     * Constructs a dag from edges and isolated vertices.
     */
    public Dag(List<Edge<T>> edges, List<T> isolatedVertices) {
        if (isolatedVertices != null && !isolatedVertices.isEmpty()) {
            startingVertices.addAll(isolatedVertices);
        }
        if (edges != null && !edges.isEmpty()) {
            for (Edge<T> edge : edges) {
                nextMap.computeIfAbsent(edge.getFrom(), k -> new HashSet<>())
                        .add(edge.getTo());
                prevMap.computeIfAbsent(edge.getTo(), k -> new HashSet<>())
                        .add(edge.getFrom());
            }
            startingVertices.addAll(nextMap.keySet());
            startingVertices.removeAll(prevMap.keySet());
        }
    }

    public Set<T> getNextVertices(T from) {
        return nextMap.getOrDefault(from, Collections.emptySet());
    }

    public Set<T> getPreviousVertices(T to) {
        return prevMap.getOrDefault(to, Collections.emptySet());
    }
}
