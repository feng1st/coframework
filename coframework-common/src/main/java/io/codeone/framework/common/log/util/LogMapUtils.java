package io.codeone.framework.common.log.util;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Objects;

/**
 * Utilities for structured logging map operations. Provides methods to help organize
 * log context data in a flattened key structure suitable for most logging systems.
 */
@UtilityClass
public class LogMapUtils {

    /**
     * Flattens a nested map into the target map using dot-notated keys. Modifies
     * the target map in-place by adding entries with combined keys in {@code "parent.child"}
     * format.
     *
     * @param map   target map to receive flattened entries (must not be null)
     * @param key   parent key prefix for nested entries (e.g., "params")
     * @param value nested map to flatten into the target map (must not be null)
     * @throws NullPointerException if either map or value argument is null
     */
    public void putNestedMap(Map<String, Object> map, String key, Map<?, ?> value) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(value);
        for (Map.Entry<?, ?> entry : value.entrySet()) {
            map.put(key + "." + entry.getKey(), entry.getValue());
        }
    }
}
