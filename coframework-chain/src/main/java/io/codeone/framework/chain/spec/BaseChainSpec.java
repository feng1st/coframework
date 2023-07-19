package io.codeone.framework.chain.spec;

import java.util.stream.Collectors;

public abstract class BaseChainSpec implements ChainSpec {

    @Override
    public String toString() {
        return getName() + getNodeClasses().stream()
                .map(Class::getSimpleName)
                .collect(Collectors.toList());
    }
}
