package io.codeone.framework.chain.extension;

import io.codeone.framework.chain.constants.Key;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.Node;
import io.codeone.framework.chain.spec.ChainSpec;
import io.codeone.framework.chain.util.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interference {

    private List<Path<Class<? extends Node>>> alterations;

    private Map<Key, Object> argumentsByKey;

    private Map<Class<?>, Object> argumentsByClass;

    public Interference addAlteration(Path<Class<? extends Node>> alteration) {
        if (alterations == null) {
            alterations = new ArrayList<>();
        }
        alterations.add(alteration);
        return this;
    }

    public Interference addArgument(Key key, Object value) {
        if (argumentsByKey == null) {
            argumentsByKey = new HashMap<>();
        }
        argumentsByKey.put(key, value);
        return this;
    }

    public Interference addArgument(Object value) {
        if (argumentsByClass == null) {
            argumentsByClass = new HashMap<>();
        }
        argumentsByClass.put(value.getClass(), value);
        return this;
    }

    public ChainSpec interfere(ChainSpec chainSpec) {
        if (alterations == null || alterations.isEmpty()) {
            return chainSpec;
        }
        return ChainSpec.of(chainSpec, alterations);
    }

    public <T> Context<T> interfere(Context<T> context) {
        if (argumentsByKey != null && !argumentsByKey.isEmpty()) {
            argumentsByKey.forEach(context::setArgument);
        }
        if (argumentsByClass != null && !argumentsByClass.isEmpty()) {
            argumentsByClass.values().forEach(context::setArgument);
        }
        return context;
    }
}
