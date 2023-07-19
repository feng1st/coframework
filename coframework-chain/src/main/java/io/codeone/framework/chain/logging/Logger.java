package io.codeone.framework.chain.logging;

import io.codeone.framework.chain.constants.Key;

public interface Logger {

    void log(String key, Object value);

    default void log(Key key, Object value) {
        log(key.toString(), value);
    }

    default void log(Class<?> clazz, Object value) {
        log(clazz.getSimpleName(), value);
    }

    default void logTarget(Object value) {
        log("target", value);
    }
}
