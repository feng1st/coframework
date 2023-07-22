package io.codeone.framework.chain.dag;

import lombok.Getter;

import java.util.*;

public class Dag<T> {

    private final Map<T, Set<T>> nextMap = new IdentityHashMap<>();

    private final Map<T, Set<T>> prevMap = new IdentityHashMap<>();

    @Getter
    private final Set<T> startingVertices = Collections.newSetFromMap(new IdentityHashMap<>());

    public Dag(List<Edge<T>> edges) {
        for (Edge<T> edge : edges) {
            nextMap.computeIfAbsent(edge.getFrom(), k -> Collections.newSetFromMap(new IdentityHashMap<>()))
                    .add(edge.getTo());
            prevMap.computeIfAbsent(edge.getTo(), k -> Collections.newSetFromMap(new IdentityHashMap<>()))
                    .add(edge.getFrom());
        }
        startingVertices.addAll(nextMap.keySet());
        startingVertices.removeAll(prevMap.keySet());
    }

    public Set<T> getNextVertices(T from) {
        return nextMap.getOrDefault(from, Collections.emptySet());
    }

    public Set<T> getPreviousVertices(T to) {
        return prevMap.getOrDefault(to, Collections.emptySet());
    }
}
