package io.codeone.framework.chain.spec;

import io.codeone.framework.chain.node.Node;

import java.util.List;

public interface ChainSpec {

    String getName();

    List<Class<? extends Node>> getNodeClasses();
}
