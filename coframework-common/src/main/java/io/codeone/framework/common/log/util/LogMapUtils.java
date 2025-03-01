package io.codeone.framework.common.log.util;

import lombok.experimental.UtilityClass;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Utilities for processing hierarchical {@link LogMap} structures. Contains:
 * <ul>
 *   <li>{@link #wrap}: Recursive transformation with type-safe handling of nested
 *   LogMaps</li>
 *   <li>{@link #flatProcess}: Flattening for formats requiring key path notation
 *   (e.g. logfmt)</li>
 * </ul>
 */
@UtilityClass
public class LogMapUtils {

    /**
     * Recursively transforms a LogMap with key/value processors. Only processes
     * nested {@link LogMap} instances (other Map implementations are treated as
     * leaf nodes).
     *
     * @param logMap       Source map to process (null-safe, returns empty map if
     *                     null)
     * @param keyWrapper   Key transformation function (e.g. sanitization)
     * @param valueWrapper Value transformation function (e.g. encoding)
     * @return New LogMap with transformed entries
     */
    public LogMap<Object, Object> wrap(LogMap<?, ?> logMap,
                                       Function<Object, Object> keyWrapper,
                                       Function<Object, Object> valueWrapper) {
        return wrap(logMap, keyWrapper, valueWrapper, new IdentityHashMap<>());
    }

    private LogMap<Object, Object> wrap(LogMap<?, ?> logMap,
                                        Function<Object, Object> keyWrapper,
                                        Function<Object, Object> valueWrapper,
                                        IdentityHashMap<Object, Object> visited) {
        if (visited.put(logMap, logMap) != null) {
            return null;
        }
        LogMap<Object, Object> wrapped = new LogMap<>();
        for (Map.Entry<?, ?> entry : logMap.entrySet()) {
            if (entry.getValue() instanceof LogMap) {
                wrapped.put(keyWrapper.apply(entry.getKey()),
                        wrap((LogMap<?, ?>) entry.getValue(), keyWrapper, valueWrapper, visited));
            } else {
                wrapped.put(keyWrapper.apply(entry.getKey()),
                        valueWrapper.apply(entry.getValue()));
            }
        }
        return wrapped;
    }

    /**
     * Flattens hierarchical LogMap into key-value pairs with dot-notation keys.
     * Suitable for formats requiring flat namespaces (e.g. logfmt).
     *
     * <pre>{@code
     * // Processes nested map {a: {b: 1}} into "a.b=1"
     * }</pre>
     *
     * @param logMap  Map to flatten
     * @param process Consumer receiving flattened entries
     */
    public void flatProcess(LogMap<?, ?> logMap, BiConsumer<Object, Object> process) {
        flatProcess(logMap, process, new IdentityHashMap<>());
    }

    private void flatProcess(LogMap<?, ?> logMap, BiConsumer<Object, Object> process,
                             IdentityHashMap<Object, Object> visited) {
        if (visited.put(logMap, logMap) != null) {
            return;
        }
        for (Map.Entry<?, ?> entry : logMap.entrySet()) {
            if (entry.getValue() instanceof LogMap) {
                flatProcess((LogMap<?, ?>) entry.getValue(),
                        (k, v) -> process.accept(entry.getKey() + "." + k, v), visited);
            } else {
                process.accept(entry.getKey(), entry.getValue());
            }
        }
    }
}