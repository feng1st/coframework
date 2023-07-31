package io.codeone.framework.chain.logging;

import io.codeone.framework.model.Key;

/**
 * Using this logger to log selected, concise, key information.
 */
public interface Logger {

    /**
     * Logs key information, as a key-value pair.
     */
    void log(String key, Object value);

    /**
     * Logs key information, using a Key.
     */
    default void log(Key key, Object value) {
        log(key.toString(), value);
    }

    /**
     * Logs key information, using class as the key.
     */
    default void log(Class<?> clazz, Object value) {
        log(clazz.getSimpleName(), value);
    }

    /**
     * Logs the target.
     */
    default void logTarget(Object value) {
        log("target", value);
    }
}
