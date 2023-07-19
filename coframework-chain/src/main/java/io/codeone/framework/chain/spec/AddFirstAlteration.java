package io.codeone.framework.chain.spec;

import io.codeone.framework.chain.node.Node;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class AddFirstAlteration implements Alteration {

    private final Class<? extends Node> nodeClass;

    @Override
    public void applyTo(List<Class<? extends Node>> nodeClasses) {
        nodeClasses.add(0, nodeClass);
    }
}
