package io.codeone.framework.chain.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
public class Path<T> {

    private final List<T> vertices;

    @SafeVarargs
    public static <T> Path<T> of(T... vertices) {
        return of(Arrays.asList(vertices));
    }

    public T toIsolatedVertex() {
        if (vertices == null || vertices.size() != 1) {
            return null;
        }
        return vertices.get(0);
    }

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
