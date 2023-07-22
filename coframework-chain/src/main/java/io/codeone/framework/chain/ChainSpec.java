package io.codeone.framework.chain;

import io.codeone.framework.chain.constants.ChainName;
import io.codeone.framework.chain.dag.Dag;
import io.codeone.framework.chain.dag.Edge;
import io.codeone.framework.chain.dag.Path;
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

@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class ChainSpec {

    @Getter
    private final ChainName name;

    private final List<Path<Class<? extends Node>>> paths;

    @SafeVarargs
    public static ChainSpec of(ChainName name, Path<Class<? extends Node>>... paths) {
        return of(name, Arrays.asList(paths));
    }

    @SafeVarargs
    public static ChainSpec of(ChainSpec source, Path<Class<? extends Node>>... paths) {
        return of(source, Arrays.asList(paths));
    }

    public static ChainSpec of(ChainSpec source, List<Path<Class<? extends Node>>> paths) {
        List<Path<Class<? extends Node>>> merged = new ArrayList<>(source.paths);
        merged.addAll(paths);
        return new ChainSpec(source.name, merged);
    }

    public Dag<Node> getNodeDag(NodeFactory nodeFactory) {
        if (paths == null || paths.isEmpty()) {
            return new Dag<>();
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
        return new Dag<>(edges, isolatedVertices);
    }

    private static <T, R> Edge<R> map(Edge<T> source, Function<T, R> function) {
        return Edge.of(function.apply(source.getFrom()), function.apply(source.getTo()));
    }
}
