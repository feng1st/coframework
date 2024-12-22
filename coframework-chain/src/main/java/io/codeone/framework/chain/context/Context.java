package io.codeone.framework.chain.context;

import io.codeone.framework.chain.log.MDC;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents the execution context for a chain of operations.
 *
 * <p>This class manages a parameter map and offers utility methods to add, retrieve,
 * and manipulate data relevant to the execution of a chain. It supports fluent
 * configurations and is thread-safe.
 */
@Accessors(fluent = true, chain = true)
public class Context {

    protected final Map<Object, Object> paramMap = new ConcurrentHashMap<>();

    /**
     * The name of the chain associated with this context.
     */
    @Getter
    @Setter
    private String chainName = "anonymous";

    /**
     * The thread pool associated with this context.
     */
    @Getter
    @Setter
    private ExecutorService threadPool;

    /**
     * The consumer to be executed before the main execution logic.
     */
    @Getter
    @Setter
    private Consumer<Context> onExecute;

    // Static factory methods

    /**
     * Creates a new empty {@code Context}.
     *
     * @return a new {@code Context} instance
     */
    public static Context of() {
        return new Context();
    }

    /**
     * Creates a new {@code Context} with a single key-value pair.
     *
     * @param k1 the key
     * @param v1 the value
     * @return a new {@code Context} instance with the given key-value pair
     */
    public static Context of(Object k1, Object v1) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        return context;
    }

    /**
     * Creates a new {@code Context} with two key-value pairs.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @return a new {@code Context} instance with the specified key-value pairs
     */
    public static Context of(Object k1, Object v1, Object k2, Object v2) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        return context;
    }

    /**
     * Creates a new {@code Context} with three key-value pairs.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @param k3 the third key
     * @param v3 the third value
     * @return a new {@code Context} instance with the specified key-value pairs
     */
    public static Context of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        context.paramMap.put(k3, v3);
        return context;
    }

    /**
     * Creates a new {@code Context} with four key-value pairs.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @param k3 the third key
     * @param v3 the third value
     * @param k4 the fourth key
     * @param v4 the fourth value
     * @return a new {@code Context} instance with the specified key-value pairs
     */
    public static Context of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        context.paramMap.put(k3, v3);
        context.paramMap.put(k4, v4);
        return context;
    }

    /**
     * Creates a new {@code Context} with five key-value pairs.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @param k3 the third key
     * @param v3 the third value
     * @param k4 the fourth key
     * @param v4 the fourth value
     * @param k5 the fifth key
     * @param v5 the fifth value
     * @return a new {@code Context} instance with the specified key-value pairs
     */
    public static Context of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4,
                             Object k5, Object v5) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        context.paramMap.put(k3, v3);
        context.paramMap.put(k4, v4);
        context.paramMap.put(k5, v5);
        return context;
    }

    /**
     * Creates a new {@code Context} with a map of key-value pairs.
     *
     * @param paramMap the map containing key-value pairs
     * @return a new {@code Context} instance with the specified parameters
     */
    public static Context of(Map<Object, Object> paramMap) {
        Context context = new Context();
        context.paramMap.putAll(paramMap);
        return context;
    }

    /**
     * Creates a new {@code Context} as a copy of another {@code Context}.
     *
     * @param context the context to copy
     * @return a new {@code Context} instance with the same parameters as the provided
     * context
     */
    public static Context of(Context context) {
        return of(context.paramMap);
    }

    /**
     * Adds or updates a key-value pair in this context.
     *
     * @param key   the key
     * @param value the value
     * @return the updated {@code Context} instance
     */
    public Context with(Object key, Object value) {
        put(key, value);
        return this;
    }

    // Parameter map operations

    /**
     * Executes an action if the specified key is present in the context.
     *
     * @param key    the key to check
     * @param action the action to execute with the associated value
     * @param <V>    the type of the value
     */
    public <V> void ifPresent(Object key, Consumer<V> action) {
        Optional.ofNullable(this.<V>tryCast(key, paramMap.get(key)))
                .ifPresent(action);
    }

    /**
     * Executes an action if the specified class type key is present in the context.
     *
     * @param key    the class type key to check
     * @param action the action to execute with the associated value
     * @param <V>    the type of the value
     */
    public <V> void ifPresent(Class<V> key, Consumer<V> action) {
        Optional.ofNullable(key.cast(paramMap.get(key)))
                .ifPresent(action);
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key
     * @param <V> the type of the value
     * @return the value associated with the key, or {@code null} if not found
     */
    public <V> V get(Object key) {
        return tryCast(key, paramMap.get(key));
    }

    /**
     * Retrieves the value associated with the specified class type key.
     *
     * @param key the class type key
     * @param <V> the expected type of the value
     * @return the value associated with the key, or {@code null} if not found
     */
    public <V> V get(Class<V> key) {
        return key.cast(paramMap.get(key));
    }

    /**
     * Retrieves the value associated with the specified key or returns a default
     * value if the key is not present.
     *
     * @param key          the key
     * @param defaultValue the default value to return if the key is not found
     * @param <V>          the type of the value
     * @return the value associated with the key, or the default value
     */
    public <V> V getOrDefault(Object key, V defaultValue) {
        return tryCast(key, paramMap.getOrDefault(key, tryCast(key, defaultValue)));
    }

    /**
     * Retrieves the value associated with the specified class type key or returns
     * a default value.
     *
     * @param key          the class type key to search for
     * @param defaultValue the default value to return if the key is not found
     * @param <V>          the type of the value
     * @return the value associated with the key, or the default value if the key
     * is not found
     */
    public <V> V getOrDefault(Class<V> key, V defaultValue) {
        return key.cast(paramMap.getOrDefault(key, key.cast(defaultValue)));
    }

    /**
     * Adds or updates a key-value pair in the context.
     *
     * @param key   the key
     * @param value the value
     * @param <V>   the type of the value
     * @return the previous value associated with the key, or {@code null} if none
     */
    public <V> V put(Object key, V value) {
        if (value != null) {
            return tryCast(key, paramMap.put(key, tryCast(key, value)));
        } else {
            return tryCast(key, paramMap.remove(key));
        }
    }

    /**
     * Adds or updates a key-value pair using a class type key.
     *
     * @param key   the class type key to update
     * @param value the value to associate with the key
     * @param <V>   the type of the value
     * @return the previous value associated with the key, or {@code null} if none
     * existed
     */
    public <V> V put(Class<V> key, V value) {
        if (value != null) {
            return key.cast(paramMap.put(key, key.cast(value)));
        } else {
            return key.cast(paramMap.remove(key));
        }
    }

    /**
     * Adds a key-value pair to the context if the key is not already present.
     *
     * @param key   the key
     * @param value the value
     * @param <V>   the type of the value
     * @return the existing value if the key is present, or the new value
     */
    public <V> V putIfAbsent(Object key, V value) {
        if (value != null) {
            return tryCast(key, paramMap.putIfAbsent(key, tryCast(key, value)));
        } else {
            return tryCast(key, paramMap.get(key));
        }
    }

    /**
     * Adds a key-value pair to the context if the specified class type key is not
     * already present.
     *
     * @param key   the class type key to check and add
     * @param value the value to associate if the key is not present
     * @param <V>   the type of the value
     * @return the current value associated with the key if present, or the new
     * value if added
     */
    public <V> V putIfAbsent(Class<V> key, V value) {
        if (value != null) {
            return key.cast(paramMap.putIfAbsent(key, key.cast(value)));
        } else {
            return key.cast(paramMap.get(key));
        }
    }

    /**
     * Removes the key-value pair associated with the specified key.
     *
     * @param key the key to remove
     * @param <V> the type of the value
     * @return the removed value, or {@code null} if no value was associated with
     * the key
     */
    public <V> V remove(Object key) {
        return tryCast(key, paramMap.remove(key));
    }

    /**
     * Removes the value associated with the specified class type key.
     *
     * @param key the class type key to remove
     * @param <V> the type of the removed value
     * @return the removed value, or {@code null} if no value was associated with
     * the key
     */
    public <V> V remove(Class<V> key) {
        return key.cast(paramMap.remove(key));
    }

    /**
     * Computes a value for the specified key if it is not already present.
     *
     * @param key             the key
     * @param mappingFunction the function to compute a value
     * @param <V>             the type of the value
     * @return the computed or existing value
     */
    public <V> V computeIfAbsent(Object key, Function<Object, ? extends V> mappingFunction) {
        return tryCast(key, paramMap.computeIfAbsent(key,
                k -> tryCast(k, mappingFunction.apply(k))));
    }

    /**
     * Computes a value for the specified class type key if it is not already present.
     *
     * @param key             the class type key
     * @param mappingFunction the function to compute a value if the key is absent
     * @param <V>             the type of the computed value
     * @return the computed value or the existing value if the key was already present
     */
    public <V> V computeIfAbsent(Class<V> key, Function<Object, ? extends V> mappingFunction) {
        return key.cast(paramMap.computeIfAbsent(key,
                k -> key.cast(mappingFunction.apply(k))));
    }

    /**
     * Executes a remapping function if the key is present in the context.
     *
     * @param key               the key
     * @param remappingFunction the function to compute a new value
     * @param <V>               the type of the value
     * @return the updated value, or {@code null} if none remains
     */
    public <V> V computeIfPresent(Object key, BiFunction<Object, ? super V, ? extends V> remappingFunction) {
        return tryCast(key, paramMap.computeIfPresent(key,
                (k, v) -> tryCast(k, remappingFunction.apply(k, tryCast(k, v)))));
    }

    /**
     * Executes a remapping function if the specified class type key is present
     * in the context.
     *
     * @param key               the class type key to check
     * @param remappingFunction the function to compute a new value
     * @param <V>               the type of the computed value
     * @return the updated value, or {@code null} if the key was not present
     */
    public <V> V computeIfPresent(Class<V> key, BiFunction<Object, ? super V, ? extends V> remappingFunction) {
        return key.cast(paramMap.computeIfPresent(key,
                (k, v) -> key.cast(remappingFunction.apply(k, key.cast(v)))));
    }

    /**
     * Executes a remapping function for the specified key.
     *
     * @param key               the key
     * @param remappingFunction the function to compute a new value
     * @param <V>               the type of the value
     * @return the updated value, or {@code null} if no mapping exists
     */
    public <V> V compute(Object key, BiFunction<Object, ? super V, ? extends V> remappingFunction) {
        return tryCast(key, paramMap.compute(key,
                (k, v) -> tryCast(k, remappingFunction.apply(k, tryCast(k, v)))));
    }

    /**
     * Computes a new value for the specified class type key, regardless of its
     * presence.
     *
     * @param key               the class type key
     * @param remappingFunction the function to compute a new value
     * @param <V>               the type of the computed value
     * @return the updated value, or {@code null} if the computation returned {@code
     * null}
     */
    public <V> V compute(Class<V> key, BiFunction<Object, ? super V, ? extends V> remappingFunction) {
        return key.cast(paramMap.compute(key,
                (k, v) -> key.cast(remappingFunction.apply(k, key.cast(v)))));
    }

    // Nested map operations (Key-Value maps)

    /**
     * Retrieves a value from a nested map associated with the specified key and
     * sub-key.
     *
     * @param key    the main key
     * @param subKey the sub-key within the nested map
     * @param <K>    the type of the sub-key
     * @param <V>    the type of the value
     * @return the value associated with the sub-key, or {@code null} if not found
     */
    public <K, V> V kvGet(Object key, K subKey) {
        Map<K, V> subMap = get(key);
        if (subMap != null) {
            return subMap.get(subKey);
        }
        return null;
    }

    /**
     * Adds or updates a key-value pair within a nested map associated with the
     * specified key.
     *
     * @param key    the main key
     * @param subKey the sub-key
     * @param value  the value to associate with the sub-key
     * @param <K>    the type of the sub-key
     * @param <V>    the type of the value
     * @return the previous value associated with the sub-key, or {@code null} if
     * none
     */
    public <K, V> V kvPut(Object key, K subKey, V value) {
        if (value != null) {
            return computeIfAbsent(key, k -> new ConcurrentHashMap<K, V>())
                    .put(subKey, value);
        }
        Map<K, V> subMap = get(key);
        if (subMap != null) {
            V oldValue = subMap.remove(subKey);
            if (subMap.isEmpty()) {
                remove(key);
            }
            return oldValue;
        }
        return null;
    }

    /**
     * Computes a value for a sub-key within a nested map if it is not already present.
     *
     * @param key             the main key
     * @param subKey          the sub-key
     * @param mappingFunction the function to compute a value
     * @param <K>             the type of the sub-key
     * @param <V>             the type of the value
     * @return the computed or existing value
     */
    public <K, V> V kvComputeIfAbsent(Object key, K subKey, Function<? super K, ? extends V> mappingFunction) {
        return computeIfAbsent(key, k -> new ConcurrentHashMap<K, V>())
                .computeIfAbsent(subKey, mappingFunction);
    }

    // Logging support

    /**
     * Adds a key-value pair to the logging context.
     *
     * @param key   the key
     * @param value the value
     */
    public void log(Object key, Object value) {
        MDC.put(key, value);
    }

    /**
     * Builds the log representation of this context.
     *
     * @param content the map to populate with log content
     */
    public void buildLog(Map<String, Object> content) {
        content.put("chain", chainName);
    }

    @SuppressWarnings("unchecked")
    private <T> T tryCast(Object key, Object obj) {
        if (key instanceof Class) {
            return ((Class<T>) key).cast(obj);
        }
        if (key instanceof Typed) {
            return ((Typed) key).cast(obj);
        }
        return (T) obj;
    }
}
