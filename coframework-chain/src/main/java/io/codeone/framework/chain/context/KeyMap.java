package io.codeone.framework.chain.context;

import io.codeone.framework.model.Key;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class KeyMap {

    private final Map<Key, Object> map = new ConcurrentHashMap<>();

    public void forEach(BiConsumer<Key, Object> consumer) {
        map.forEach(consumer);
    }

    public final <T> void ifPresent(Key key, Consumer<T> valueConsumer) {
        Optional.ofNullable(key.<T>cast(map.get(key)))
                .ifPresent(valueConsumer);
    }

    public final boolean containsKey(Key key) {
        return map.containsKey(key);
    }

    public final <T> T get(Key key) {
        return key.cast(map.get(key));
    }

    public final <T> T getOrDefault(Key key, T defaultValue) {
        return key.cast(map.getOrDefault(key, key.cast(defaultValue)));
    }

    public final <T> T put(Key key, T value) {
        if (value == null) {
            return key.cast(map.remove(key));
        }
        return key.cast(map.put(key, key.cast(value)));
    }

    public final <T> T putIfAbsent(Key key, T value) {
        if (value == null) {
            return null;
        }
        return key.cast(map.putIfAbsent(key, key.cast(value)));
    }

    public final <T> T supplyIfAbsent(Key key, Supplier<T> valueSupplier) {
        return key.cast(map.computeIfAbsent(key,
                k -> k.cast(valueSupplier.get())));
    }

    public final <T> T updateIfPresent(Key key, Function<T, T> valueUpdater) {
        return key.cast(map.computeIfPresent(key,
                (k, v) -> k.cast(valueUpdater.apply(k.cast(v)))));
    }

    public final <T> T update(Key key, Function<T, T> valueUpdater) {
        return key.cast(map.compute(key,
                (k, v) -> k.cast(valueUpdater.apply(k.cast(v)))));
    }

    public final <T> T remove(Key key) {
        return key.cast(map.remove(key));
    }

    public final <K, V> V kkvGet(Key key, K subKey) {
        return Optional.ofNullable(this.<Map<K, V>>get(key))
                .map(subMap -> subMap.get(subKey))
                .orElse(null);
    }

    public final <K, V> V kkvPut(Key key, K subKey, V value) {
        if (value == null) {
            return Optional.ofNullable(this.<Map<K, V>>get(key))
                    .map(subMap -> subMap.remove(subKey))
                    .orElse(null);
        }
        return supplyIfAbsent(key, () -> new ConcurrentHashMap<K, V>())
                .put(subKey, value);
    }

    public final <K, V> V kkvSupplyIfAbsent(Key key, K subKey,
                                            Supplier<V> valueSupplier) {
        return supplyIfAbsent(key, () -> new ConcurrentHashMap<K, V>())
                .computeIfAbsent(subKey, k -> valueSupplier.get());
    }
}
