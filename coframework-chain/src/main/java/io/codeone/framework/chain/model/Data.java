package io.codeone.framework.chain.model;

import io.codeone.framework.chain.constants.Key;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

@NoArgsConstructor(staticName = "of")
@ToString
public class Data {

    private final Map<String, Object> data = new ConcurrentHashMap<>();

    public boolean has(Key key) {
        return data.containsKey(key.getKey());
    }

    public <P> P get(Key key) {
        return key.<P>getClazz().cast(data.get(key.getKey()));
    }

    public <P> P getOrDefault(Key key, P defaultValue) {
        return key.<P>getClazz().cast(data.getOrDefault(key.getKey(), defaultValue));
    }

    public Data set(Key key, Object value) {
        data.put(key.getKey(), key.getClazz().cast(value));
        return this;
    }

    public Data setIfAbsent(Key key, Object value) {
        data.putIfAbsent(key.getKey(), key.getClazz().cast(value));
        return this;
    }

    public Data setIfAbsent(Key key, Supplier<?> valueSupplier) {
        data.computeIfAbsent(key.getKey(), k -> key.getClazz().cast(valueSupplier.get()));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> Data update(Key key, Function<P, P> valueUpdater) {
        data.computeIfPresent(key.getKey(), (k, v) -> key.getClazz().cast(valueUpdater.apply((P) v)));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> Data setOrUpdate(Key key, Function<P, P> valueSetter) {
        data.compute(key.getKey(), (k, v) -> key.getClazz().cast(valueSetter.apply((P) v)));
        return this;
    }

    public Data reset(Key key) {
        data.remove(key.getKey());
        return this;
    }

    public Data copyFromParameter(Context<?> context, Key key) {
        setOrUpdate(key, v -> context.getArgument(key));
        return this;
    }

    public Data copyToParameter(Context<?> context, Key key) {
        context.setOrUpdateArgument(key, v -> get(key));
        return this;
    }
}
