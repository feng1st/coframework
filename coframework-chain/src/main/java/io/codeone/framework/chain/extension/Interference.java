package io.codeone.framework.chain.extension;

import io.codeone.framework.chain.ChainSpec;
import io.codeone.framework.chain.constants.Key;
import io.codeone.framework.chain.dag.Path;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interference {

    private List<Path<Class<? extends Node>>> paths;

    private Map<Key, Object> argumentsByKey;

    private Map<Class<?>, Object> argumentsByClass;

    public Interference addPath(Path<Class<? extends Node>> path) {
        if (paths == null) {
            paths = new ArrayList<>();
        }
        paths.add(path);
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
        if (paths == null || paths.isEmpty()) {
            return chainSpec;
        }
        return ChainSpec.of(chainSpec, paths);
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
