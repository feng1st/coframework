package io.codeone.framework.chain.spec;

import io.codeone.framework.chain.node.Node;

import java.util.List;

public interface Alteration {

    void applyTo(List<Class<? extends Node>> nodeClasses);
}
