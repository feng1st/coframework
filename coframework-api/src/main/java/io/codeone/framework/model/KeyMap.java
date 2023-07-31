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
     * order to include the namespace of the Key.
     */
    private final Map<String, Object> data = new ConcurrentHashMap<>();

    public boolean contains(Key key) {
        return data.containsKey(key.getKey());
    }

    public <P> P get(Key key) {
        return key.<P>getClazz().cast(data.get(key.getKey()));
    }

    public <P> P getOrDefault(Key key, P defaultValue) {
        return key.<P>getClazz().cast(data.getOrDefault(key.getKey(), defaultValue));
    }

    public KeyMap put(Key key, Object value) {
        data.put(key.getKey(), key.getClazz().cast(value));
        return this;
    }

    public KeyMap putIfAbsent(Key key, Object value) {
        data.putIfAbsent(key.getKey(), key.getClazz().cast(value));
        return this;
    }

    public KeyMap putIfAbsent(Key key, Supplier<?> valueSupplier) {
        data.computeIfAbsent(key.getKey(), k -> key.getClazz().cast(valueSupplier.get()));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> KeyMap updateIfPresent(Key key, Function<P, P> valueUpdater) {
        data.computeIfPresent(key.getKey(), (k, v) -> key.getClazz().cast(valueUpdater.apply((P) v)));
        return this;
    }

    /**
     * Sets a new value if there is no existing one, otherwise updates the
     * existing one.
     *
     * @param valueSetter The function that takes the existing value as the
     *                    input and output a new one. A null input indicates
     *                    that there was no value associated with this key.
     */
    @SuppressWarnings("unchecked")
    public <P> KeyMap putOrUpdate(Key key, Function<P, P> valueSetter) {
        data.compute(key.getKey(), (k, v) -> key.getClazz().cast(valueSetter.apply((P) v)));
        return this;
    }

    public KeyMap remove(Key key) {
        data.remove(key.getKey());
        return this;
    }
}
