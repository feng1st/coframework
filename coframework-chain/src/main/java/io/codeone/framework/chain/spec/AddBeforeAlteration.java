package io.codeone.framework.chain.spec;

import io.codeone.framework.chain.node.Node;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class AddBeforeAlteration implements Alteration {

    private final Class<? extends Node> reference;

    private final Class<? extends Node> nodeClass;

    @Override
    public void applyTo(List<Class<? extends Node>> nodeClasses) {
        int index = nodeClasses.lastIndexOf(reference);
        if (index == -1) {
            throw new IllegalArgumentException("Could not find the reference '" + reference.getSimpleName() + "'");
        }
        nodeClasses.add(index, nodeClass);
    }
}
