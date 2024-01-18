package io.codeone.framework.chain.logging;

import io.codeone.framework.model.Key;

@FunctionalInterface
public interface KvLogger {

    void log(String key, Object value);

    default void log(Key key, Object value) {
        log(key.toString(), value);
    }
}
