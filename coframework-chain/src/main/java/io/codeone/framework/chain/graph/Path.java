package io.codeone.framework.chain.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a path in a graph. It comprises a series of edges, or one
 * isolated vertex.
 *
 * @param <T> The type of the vertices.
 */
@RequiredArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
public class Path<T> {

    private final List<T> vertices;

    @SafeVarargs
    public static <T> Path<T> of(T... vertices) {
        return of(Arrays.asList(vertices));
    }

    /**
     * Returns the isolated vertex if there is only one vertex, otherwise
     * returns null.
     */
    public T toIsolatedVertex() {
        if (vertices == null || vertices.size() != 1) {
            return null;
        }
        return vertices.get(0);
    }

    /**
     * Returns the composed edges if there is more than one vertex, otherwise
     * returns an empty list.
     */
    public List<Edge<T>> toEdges() {
        if (vertices == null || vertices.size() <= 1) {
            return Collections.emptyList();
        }
        List<Edge<T>> edges = new ArrayList<>();
        T from = vertices.get(0);
        for (int i = 1; i < vertices.size(); i++) {
            T to = vertices.get(i);
            edges.add(Edge.of(from, to));
            from = to;
        }
        return edges;
    }
}
