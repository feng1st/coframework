package io.codeone.framework.chain.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;

@EqualsAndHashCode
public class Graph<T> {

    private final Map<T, Set<T>> forwardEdges = new HashMap<>();

    private final Map<T, Set<T>> backwardEdges = new HashMap<>();

    @Getter
    private final Set<T> headVertices = new HashSet<>();

    @Getter
    private final Set<T> tailVertices = new HashSet<>();

    public void addVertex(T vertex) {
        if (forwardEdges.containsKey(vertex)
                || backwardEdges.containsKey(vertex)) {
            return;
        }

        headVertices.add(vertex);
        tailVertices.add(vertex);
    }

    public void addEdge(Edge<T> edge) {
        forwardEdges.computeIfAbsent(edge.getStarting(), k -> new HashSet<>())
                .add(edge.getEnding());

        backwardEdges.computeIfAbsent(edge.getEnding(), k -> new HashSet<>())
                .add(edge.getStarting());

        if (!backwardEdges.containsKey(edge.getStarting())) {
            headVertices.add(edge.getStarting());
        }
        headVertices.remove(edge.getEnding());

        if (!forwardEdges.containsKey(edge.getEnding())) {
            tailVertices.add(edge.getEnding());
        }
        tailVertices.remove(edge.getStarting());
    }

    public Set<T> getNextVertices(T startingVertex) {
        return forwardEdges.getOrDefault(startingVertex, Collections.emptySet());
    }

    public Set<T> getPreviousVertices(T endingVertex) {
        return backwardEdges.getOrDefault(endingVertex, Collections.emptySet());
    }

    public <R> Graph<R> transform(Function<T, R> function) {
        Graph<R> graph = new Graph<>();
        forwardEdges.forEach((startingVertex, endingVertices)
                -> endingVertices.forEach(endingVertex
                -> graph.addEdge(Edge.of(
                function.apply(startingVertex),
                function.apply(endingVertex)))));
        headVertices.forEach(vertex
                -> graph.addVertex(function.apply(vertex)));
        return graph;
    }

    public void merge(Graph<T> graph) {
        graph.forwardEdges.forEach((startingVertex, endingVertices)
                -> endingVertices.forEach(endingVertex
                -> addEdge(Edge.of(startingVertex, endingVertex))));
        graph.headVertices.forEach(this::addVertex);
    }

    public void concat(Graph<T> graph) {
        Set<T> tailVertices = new HashSet<>(getTailVertices());
        Set<T> headVertices = new HashSet<>(graph.getHeadVertices());
        tailVertices.forEach(startingVertex
                -> headVertices.forEach(endingVertex
                -> addEdge(Edge.of(startingVertex, endingVertex))));
        merge(graph);
    }

    public Optional<List<T>> detectCycle() {
        Set<T> visited = new HashSet<>();
        Set<T> recStack = new HashSet<>();
        Map<T, T> parentMap = new HashMap<>();

        for (T vertex : forwardEdges.keySet()) {
            if (detectCycleUtil(vertex, visited, recStack, parentMap)) {
                return Optional.of(constructCycle(vertex, parentMap));
            }
        }
        return Optional.empty();
    }

    private boolean detectCycleUtil(T current,
                                    Set<T> visited,
                                    Set<T> recStack,
                                    Map<T, T> parentMap) {
        if (recStack.contains(current)) {
            return true;
        }
        if (visited.contains(current)) {
            return false;
        }
        visited.add(current);
        recStack.add(current);

        for (T neighbor : getNextVertices(current)) {
            parentMap.put(neighbor, current);
            if (detectCycleUtil(neighbor, visited, recStack, parentMap)) {
                return true;
            }
        }
        recStack.remove(current);
        return false;
    }

    private List<T> constructCycle(T startVertex, Map<T, T> parentMap) {
        List<T> cycle = new ArrayList<>();
        T current = startVertex;
        while (!cycle.contains(current)) {
            cycle.add(current);
            current = parentMap.get(current);
        }
        cycle.add(current);
        Collections.reverse(cycle);
        return cycle;
    }
}
