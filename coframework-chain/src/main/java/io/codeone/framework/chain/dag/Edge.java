package io.codeone.framework.chain.dag;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
public class Edge<T> {

    private final T from;

    private final T to;
}
