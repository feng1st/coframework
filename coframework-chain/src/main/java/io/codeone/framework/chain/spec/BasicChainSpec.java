package io.codeone.framework.chain.spec;

import io.codeone.framework.chain.node.Node;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = false)
public class BasicChainSpec extends BaseChainSpec {

    @Getter
    private final String name;

    @Getter
    private final List<Class<? extends Node>> nodeClasses;

    @SafeVarargs
    public static BasicChainSpec of(String name, Class<? extends Node>... nodeClasses) {
        return new BasicChainSpec(name, Arrays.asList(nodeClasses));
    }
}
