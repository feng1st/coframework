package io.codeone.framework.chain.spec;

import io.codeone.framework.chain.node.Node;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
public class DerivedChainSpec extends BaseChainSpec {

    private final ChainSpec source;

    private final List<Alteration> alterations;

    @Getter
    private final transient String name;

    private transient List<Class<? extends Node>> nodeClasses;

    public static DerivedChainSpec of(ChainSpec source, Alteration... alterations) {
        return new DerivedChainSpec(source, Arrays.asList(alterations));
    }

    public static DerivedChainSpec of(ChainSpec source, List<Alteration> alterations) {
        return new DerivedChainSpec(source, alterations);
    }

    private DerivedChainSpec(ChainSpec source, List<Alteration> alterations) {
        this.source = source;
        this.alterations = alterations;

        this.name = source.getName();
    }

    @Override
    public List<Class<? extends Node>> getNodeClasses() {
        List<Class<? extends Node>> classes;
        if ((classes = nodeClasses) == null) {
            synchronized (this) {
                if ((classes = nodeClasses) == null) {
                    classes = new LinkedList<>(source.getNodeClasses());
                    for (Alteration alteration : alterations) {
                        alteration.applyTo(classes);
                    }
                    nodeClasses = classes;
                }
            }
        }
        return classes;
    }
}
