package io.codeone.framework.common.log.util;

import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Specialized map implementation for structured logging contexts with these features:
 * <ul>
 *   <li>Maintains strict insertion order
 *   <li>Supports hierarchical nesting via {@link LogMap} instances
 * </ul>
 *
 * <p>When nested maps need to preserve structured format (e.g. JSON), wrap them
 * with {@link LogMap}, for example:
 * <pre>{@code
 * logMap.put("ctx", new LogMap(context));  // Preserves structure
 * logMap.put("result", rawMap);           // Treated as leaf node
 * }</pre>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
@NoArgsConstructor
public class LogMap<K, V> extends LinkedHashMap<K, V> {

    /**
     * Initializes from existing map entries.
     *
     * @param m the source map entries
     */
    public LogMap(Map<? extends K, ? extends V> m) {
        super(m);
    }
}
