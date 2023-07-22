package io.codeone.framework.chain.dag;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Edge<T> {

    private final T from;

    private final T to;
}
