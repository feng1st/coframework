package io.codeone.framework.chain;

import io.codeone.framework.chain.constants.ChainName;
import io.codeone.framework.chain.graph.Edge;
import io.codeone.framework.chain.graph.Graph;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.node.Node;
import io.codeone.framework.chain.node.NodeFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The spec of a chain, that comprises the name of the chain and the graph of
 * its nodes.
 * <p>
 * The graph a chain needs is supposed to be a Directed Acyclic Graph (DAG),
 * and it is the developers' responsibility to provide an acyclic one.
 * PLEASE PAY ATTENTION: All nodes in a cycle will be simply OMITTED WITHOUT
 * EXCEPTION if a cyclic graph is given.
 * <p>
 * The spec does not store the graph itself, instead, it stores all paths of
 * that graph, in order to be prepared and altered easily.
 * <p>
 * The instance of this class is the key of a cache, and that is why the class
 * itself and its attributes implemented the equals() and hashCode() methods.
 */
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class ChainSpec {

    @Getter
    private final ChainName name;

    /**
     * The paths that comprise the graph of nodes.
     */
    private final List<Path<Class<? extends Node>>> paths;

    @SafeVarargs
    public static ChainSpec of(ChainName name, Path<Class<? extends Node>>... paths) {
        return of(name, Arrays.asList(paths));
    }

    @SafeVarargs
    public static ChainSpec of(ChainSpec source, Path<Class<? extends Node>>... paths) {
        return of(source, Arrays.asList(paths));
    }

    /**
     * Constructs a chain spec by extending another spec.
     */
    public static ChainSpec of(ChainSpec source, List<Path<Class<? extends Node>>> paths) {
        List<Path<Class<? extends Node>>> merged = new ArrayList<>(source.paths);
        merged.addAll(paths);
        return new ChainSpec(source.name, merged);
    }

    /**
     * To build a graph of nodes from this spec.
     * <p>
     * The graph a chain needs is supposed to be a Directed Acyclic Graph
     * (DAG), and it is the developers' responsibility to provide an acyclic
     * one. PLEASE PAY ATTENTION: All nodes in a cycle will be simply OMITTED
     * WITHOUT EXCEPTION if a cyclic graph is given.
     */
    public Graph<Node> getNodeGraph(NodeFactory nodeFactory) {
        if (paths == null || paths.isEmpty()) {
            return new Graph<>();
        }
        List<Edge<Node>> edges = paths.stream()
                .map(Path::toEdges)
                .flatMap(List::stream)
                .map(o -> map(o, nodeFactory::getNode))
                .collect(Collectors.toList());
        List<Node> isolatedVertices = paths.stream()
                .map(Path::toIsolatedVertex)
                .filter(Objects::nonNull)
                .map(nodeFactory::getNode)
                .collect(Collectors.toList());
        return new Graph<>(edges, isolatedVertices);
    }

    private static <T, R> Edge<R> map(Edge<T> source, Function<T, R> function) {
        return Edge.of(function.apply(source.getStarting()), function.apply(source.getEnding()));
    }
}
