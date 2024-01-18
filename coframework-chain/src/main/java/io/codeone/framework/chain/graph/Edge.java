package io.codeone.framework.chain.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public final class Edge<T> {

    private final T starting;

    private final T ending;
}
