package io.codeone.framework.chain;

import io.codeone.framework.api.context.Key;
import io.codeone.framework.api.context.KeyMap;
import io.codeone.framework.chain.logging.KvLogger;

import java.util.Map;

public class Context extends KeyMap {

    private final String name;

    public Context() {
        this(null);
    }

    public Context(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void putAll(Map<Key, Object> map) {
        if (map == null) {
            return;
        }
        map.forEach(this::put);
    }

    public void onEnterChain(KvLogger logger) {
        forEach(logger::log);
    }

    public void onEnterNode(KvLogger logger) {
    }
}
