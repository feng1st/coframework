package io.codeone.framework.chain.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;

/**
 * This is an implementation of a graph.
 *
 * @param <T> The type of its vertices.
 */
@EqualsAndHashCode
public class Graph<T> {

    /**
     * The staring to ending vertices map.
     */
    private final Map<T, Set<T>> nextMap = new HashMap<>();

    /**
     * The ending to starting vertices map.
     */
    private final Map<T, Set<T>> previousMap = new HashMap<>();

    /**
     * Starting vertices of the graph.
     */
    @Getter
    private final Set<T> startingVertices = new HashSet<>();

    /**
     * Constructs an empty graph.
     */
    public Graph() {
    }

    /**
     * Constructs a graph with only one vertex.
     */
    public Graph(T isolatedVertex) {
        startingVertices.add(isolatedVertex);
    }

    /**
     * Constructs a graph from edges.
     */
    public Graph(List<Edge<T>> edges) {
        for (Edge<T> edge : edges) {
            nextMap.computeIfAbsent(edge.getStarting(), k -> new HashSet<>())
                    .add(edge.getEnding());
            previousMap.computeIfAbsent(edge.getEnding(), k -> new HashSet<>())
                    .add(edge.getStarting());
        }
        startingVertices.addAll(nextMap.keySet());
        startingVertices.removeAll(previousMap.keySet());
    }

    /**
     * Constructs a graph from edges and isolated vertices.
     */
    public Graph(List<Edge<T>> edges, List<T> isolatedVertices) {
        if (isolatedVertices != null && !isolatedVertices.isEmpty()) {
            startingVertices.addAll(isolatedVertices);
        }
        if (edges != null && !edges.isEmpty()) {
            for (Edge<T> edge : edges) {
                nextMap.computeIfAbsent(edge.getStarting(), k -> new HashSet<>())
                        .add(edge.getEnding());
                previousMap.computeIfAbsent(edge.getEnding(), k -> new HashSet<>())
                        .add(edge.getStarting());
            }
            startingVertices.addAll(nextMap.keySet());
            startingVertices.removeAll(previousMap.keySet());
        }
    }

    /**
     * Returns all ending vertices of the edges that starting with the given
     * vertex, i.e. all 'v's of the given 'u' in every pair of vertices(u, v).
     */
    public Set<T> getNextVertices(T from) {
        return nextMap.getOrDefault(from, Collections.emptySet());
    }

    /**
     * Returns all starting vertices of the edges that ending with the given
     * vertex, i.e. all 'u's of the given 'v' in every pair of vertices(u, v).
     */
    public Set<T> getPreviousVertices(T to) {
        return previousMap.getOrDefault(to, Collections.emptySet());
    }
}
