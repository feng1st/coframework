package io.codeone.framework.plugin.chain;

import io.codeone.framework.plugin.util.Invokable;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Contains a {@code ThreadLocal} map that can be accessed by all plugins in the
 * same chain. The key type of the map is {@code String} and the value type is
 * {@code Object}.
 */
@UtilityClass
@SuppressWarnings("unchecked")
public class PluginChainContext {

    private final ThreadLocal<Map<String, Object>> MAP = ThreadLocal.withInitial(HashMap::new);

    /**
     * Used by the framework to execute a plugin chain within this context. The
     * content of the context will be cleared after the invocation.
     *
     * @param invokable the invocation of the plugin chain including the target
     *                  method
     * @return the result of the plugin chain or the target method
     * @throws Throwable any exception thrown by the plugin chain or the target
     *                   method
     */
    Object invoke(Invokable<?> invokable) throws Throwable {
        try {
            return invokable.invoke();
        } finally {
            MAP.remove();
        }
    }

    /**
     * Returns whether this context contain the specified key.
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this context contains a value for the specified
     * key
     */
    public boolean containsKey(String key) {
        return MAP.get().containsKey(key);
    }

    /**
     * Returns the value of the given key from the context.
     *
     * @param key the key whose associated value is to be returned
     * @param <T> the type of the value
     * @return the value to which the specified key is mapped, or null if this
     * context contains no value for the key
     */
    public <T> T get(String key) {
        return (T) MAP.get().get(key);
    }

    /**
     * Adds a key-value pair to the context. If the context previously contained
     * a value for the key, the old value is replaced by the specified value.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @param <T>   the type of the value
     * @return the previous value associated with the key, or null if there was
     * no value for the key (A null return can also indicate that the context
     * previously associated null with the key.)
     */
    public <T> T put(String key, T value) {
        return (T) MAP.get().put(key, value);
    }

    /**
     * Removes the specified key and its associated value from the context.
     * Returns the value to which this context previously associated the key, or
     * null if the context contained no value for the key.
     *
     * @param key key whose associated value is to be removed from the map
     * @param <T> the type of the value
     * @return the previous value associated with the key, or null if there was
     * no value for this key
     */
    public <T> T remove(String key) {
        return (T) MAP.get().remove(key);
    }

    /**
     * Returns the value associated with the key in the context, or
     * {@code defaultValue} if there is no value for the given key.
     *
     * @param key          the key whose associated value is to be returned
     * @param defaultValue the default value of the key
     * @param <T>          the type of the value
     * @return the value to which the specified key is mapped, or
     * {@code defaultValue} if this context contains no value for the key
     */
    public <T> T getOrDefault(String key, T defaultValue) {
        return (T) MAP.get().getOrDefault(key, defaultValue);
    }

    /**
     * Adds a value to the context, if and only if there was no associated value
     * for the given key.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @param <T>   the type of the value
     * @return the previous value associated with the specified key, or null if
     * there was no value for the key. (A null return can also indicate that the
     * context previously associated null with the key.)
     */
    public <T> T putIfAbsent(String key, T value) {
        return (T) MAP.get().putIfAbsent(key, value);
    }

    /**
     * Removes the value for the specified key only if it is currently mapped to
     * the specified value.
     *
     * @param key   key with which the specified value is associated
     * @param value value expected to be associated with the specified key
     * @return {@code true} if the value was removed
     */
    public boolean remove(String key, Object value) {
        return MAP.get().remove(key, value);
    }

    /**
     * Replaces the value for the specified key only if currently mapped to the
     * specified value.
     *
     * @param key      key with which the specified value is associated
     * @param oldValue value expected to be associated with the specified key
     * @param newValue value to be associated with the specified key
     * @param <T>      the type of the value
     * @return {@code true} if the value was replaced
     */
    public <T> boolean replace(String key, T oldValue, T newValue) {
        return MAP.get().replace(key, oldValue, newValue);
    }

    /**
     * Replaces the value for the specified key only if it is currently mapped
     * to some value.
     *
     * @param key   key with which the specified value is associated
     * @param value value to be associated with the specified key
     * @param <T>   the type of the value
     * @return the previous value associated with the specified key, or null if
     * there was no value for the key. (A null return can also indicate that the
     * map previously associated null with the key.)
     */
    public <T> T replace(String key, T value) {
        return (T) MAP.get().replace(key, value);
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to null), attempts to compute its value using the given mapping function
     * and enters it into this context unless null.
     *
     * <p>If the function returns null no mapping is recorded. If the function
     * itself throws an (unchecked) exception, the exception is rethrown, and no
     * mapping is recorded.
     *
     * @param key             key with which the specified value is to be
     *                        associated
     * @param mappingFunction the function to compute a value
     * @param <T>             the type of the value
     * @return the current (existing or computed) value associated with
     * the specified key, or null if the computed value is null
     */
    public <T> T computeIfAbsent(String key, Function<String, T> mappingFunction) {
        return (T) MAP.get().computeIfAbsent(key, mappingFunction);
    }

    /**
     * If the value for the specified key is present and non-null, attempts to
     * compute a new mapping given the key and its current mapped value.
     *
     * <p>If the function returns null, the mapping is removed. If the function
     * itself throws an (unchecked) exception, the exception is rethrown, and
     * the current mapping is left unchanged.
     *
     * @param key               key with which the specified value is to be
     *                          associated
     * @param remappingFunction the function to compute a value
     * @param <T>               the type of the value
     * @return the new value associated with the specified key, or null if none
     */
    public <T> T computeIfPresent(String key, BiFunction<String, Object, T> remappingFunction) {
        return (T) MAP.get().computeIfPresent(key, remappingFunction);
    }

    /**
     * Attempts to compute a mapping for the specified key and its current
     * mapped value (or null if there is no current mapping).
     *
     * <p>If the function returns null, the mapping is removed (or remains
     * absent if initially absent). If the function itself throws an (unchecked)
     * exception, the exception is rethrown, and the current mapping is left
     * unchanged.
     *
     * @param key               key with which the specified value is to be
     *                          associated
     * @param remappingFunction the function to compute a value
     * @param <T>               the type of the value
     * @return the new value associated with the specified key, or null if none
     */
    public <T> T compute(String key, BiFunction<String, Object, T> remappingFunction) {
        return (T) MAP.get().compute(key, remappingFunction);
    }
}
