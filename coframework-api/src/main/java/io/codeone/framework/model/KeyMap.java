package io.codeone.framework.model;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class represents a container of dynamic, extensible data, and can be
 * accessed via {@link Key}.
 */
@NoArgsConstructor
@ToString
public class KeyMap {

    /**
     * The map that stores the data. It uses {@link Key#getKey()} as the key in
     * order to include both namespace and code.
     */
    private final Map<String, Object> data = new ConcurrentHashMap<>();

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key.
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified
     * key
     */
    public boolean contains(Key key) {
        return data.containsKey(key.getKey());
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. Throws an {@code ClassCastException}
     * if the type of the existing value does not match with the
     * {@link Key#getClazz()}.
     *
     * @param key the key whose associated value is to be returned
     * @param <T> the type of the value
     * @return the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key
     * @throws ClassCastException if the type of the existing value does not
     *                            match with the {@code Key#getClazz()}
     */
    public <T> T get(Key key) {
        return key.<T>getClazz().cast(data.get(key.getKey()));
    }

    /**
     * Returns the value to which the specified key is mapped, or
     * {@code defaultValue} if this map contains no mapping for the key. Throws
     * an {@code ClassCastException} if the type of the existing value or
     * {@code defaultValue} does not match with the {@link Key#getClazz()}.
     *
     * @param key          the key whose associated value is to be returned
     * @param defaultValue the default mapping of the key
     * @param <T>          the type of the value
     * @return the value to which the specified key is mapped, or
     * {@code defaultValue} if this map contains no mapping for the key
     * @throws ClassCastException if the type of the existing value or
     *                            {@code defaultValue} does not match with the
     *                            {@code Key#getClazz()}
     */
    public <T> T getOrDefault(Key key, T defaultValue) {
        return key.<T>getClazz().cast(data.getOrDefault(key.getKey(), defaultValue));
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to null), attempts to supply its value using the given supplier function
     * and enters it into this map unless null. Then returning the current value
     * or null if now absent. Throws an {@code ClassCastException} if the result
     * type of the supplier function does not match with the
     * {@link Key#getClazz()}.
     *
     * <p>If the supplier function returns null, no mapping is recorded. If the
     * supplier function itself throws an (unchecked) exception, the exception
     * is rethrown, and no mapping is recorded.
     *
     * <p>The supplier function should not modify this map during computation.
     *
     * @param key           key with which the specified value is to be
     *                      associated
     * @param valueSupplier the supplier function to supply a new value
     * @param <T>           the type of the value
     * @return the current (existing or computed) value associated with
     * the specified key, or null if the computed value is null
     * @throws ClassCastException if the result type of the supplier function
     *                            does not match with the {@link Key#getClazz()}
     */
    @SuppressWarnings("unchecked")
    public <T> T computeIfAbsent(Key key, Supplier<T> valueSupplier) {
        return (T) data.computeIfAbsent(key.getKey(), k -> key.getClazz().cast(valueSupplier.get()));
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value. Throws an {@code ClassCastException} if the type
     * of the specified value does not match with the {@link Key#getClazz()}.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return this {@code KeyMap} (chaining)
     * @throws ClassCastException if the type of the specified value does not
     *                            match with the {@link Key#getClazz()}
     */
    public KeyMap put(Key key, Object value) {
        data.put(key.getKey(), key.getClazz().cast(value));
        return this;
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to null) associates it with the given value. Throws an
     * {@code ClassCastException} if the type of the specified value does not
     * match with the {@link Key#getClazz()}.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return this {@code KeyMap} (chaining)
     * @throws ClassCastException if the type of the specified value does not
     *                            match with the {@link Key#getClazz()}
     */
    public KeyMap putIfAbsent(Key key, Object value) {
        data.putIfAbsent(key.getKey(), key.getClazz().cast(value));
        return this;
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to null), attempts to supply its value using the given supplier function
     * and enters it into this map unless null. Throws an
     * {@code ClassCastException} if the result type of the supplier function
     * does not match with the {@link Key#getClazz()}.
     *
     * <p>If the supplier function returns null, no mapping is recorded. If the
     * supplier function itself throws an (unchecked) exception, the exception
     * is rethrown, and no mapping is recorded.
     *
     * <p>The supplier function should not modify this map during computation.
     *
     * @param key           key with which the specified value is to be
     *                      associated
     * @param valueSupplier the supplier function to supply a new value
     * @return this {@code KeyMap} (chaining)
     * @throws ClassCastException if the result type of the supplier function
     *                            does not match with the {@link Key#getClazz()}
     */
    public KeyMap putIfAbsent(Key key, Supplier<?> valueSupplier) {
        data.computeIfAbsent(key.getKey(), k -> key.getClazz().cast(valueSupplier.get()));
        return this;
    }

    /**
     * If the value for the specified key is present and non-null, attempts to
     * update the current mapped value. Throws an {@code ClassCastException} if
     * the result type of the remapping function does not match with the
     * {@link Key#getClazz()}.
     *
     * <p>If the remapping function returns null, the mapping is removed. If the
     * remapping function itself throws an (unchecked) exception, the exception
     * is rethrown, and the current mapping is left unchanged.
     *
     * <p>The remapping function should not modify this map during computation.
     *
     * @param key          key with which the specified value is to be
     *                     associated
     * @param valueUpdater the remapping function to take an existing value and
     *                     return a new one
     * @param <T>          the type of the value
     * @return this {@code KeyMap} (chaining)
     * @throws ClassCastException if the result type of the remapping function
     *                            does not match with the {@link Key#getClazz()}
     */
    @SuppressWarnings("unchecked")
    public <T> KeyMap updateIfPresent(Key key, Function<T, T> valueUpdater) {
        data.computeIfPresent(key.getKey(), (k, v) -> key.getClazz().cast(valueUpdater.apply((T) v)));
        return this;
    }

    /**
     * Attempts to compute a mapping for the specified key and its current
     * mapped value (or null if there is no current mapping). Throws an
     * {@code ClassCastException} if the result type of the remapping function
     * does not match with the {@link Key#getClazz()}.
     *
     * <p>If the remapping function returns null, the mapping is removed (or
     * remains absent if initially absent). If the remapping function itself
     * throws an (unchecked) exception, the exception is rethrown, and the
     * current mapping is left unchanged.
     *
     * <p>The remapping function should not modify this map during computation.
     *
     * @param key         key with which the specified value is to be associated
     * @param valueSetter the remapping function to take an existing value (or
     *                    null if there is no current mapping) and return a new
     *                    one
     * @param <T>         the type of the value
     * @return this {@code KeyMap} (chaining)
     * @throws ClassCastException if the result type of the remapping function
     *                            does not match with the {@link Key#getClazz()}
     */
    @SuppressWarnings("unchecked")
    public <T> KeyMap putOrUpdate(Key key, Function<T, T> valueSetter) {
        data.compute(key.getKey(), (k, v) -> key.getClazz().cast(valueSetter.apply((T) v)));
        return this;
    }

    /**
     * Removes the mapping for a key from this map if it is present (optional
     * operation).
     *
     * <p>The map will not contain a mapping for the specified key once the call
     * returns.
     *
     * @param key key whose mapping is to be removed from the map
     * @return this {@code KeyMap}
     */
    public KeyMap remove(Key key) {
        data.remove(key.getKey());
        return this;
    }
}
