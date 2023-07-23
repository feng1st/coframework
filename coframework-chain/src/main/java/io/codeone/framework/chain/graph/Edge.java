package io.codeone.framework.chain.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An edge represents a pair of vertices in a graph.
 *
 * @param <T> The type of its vertices.
 */
@RequiredArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
public class Edge<T> {

    /**
     * The starting vertex of the edge.
     */
    private final T starting;

    /**
     * The ending vertex of the edge.
     */
    private final T ending;
}
