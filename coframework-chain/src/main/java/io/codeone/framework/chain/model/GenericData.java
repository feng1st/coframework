package io.codeone.framework.chain.model;

import io.codeone.framework.chain.constants.Key;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class GenericData {

    private final Map<String, Object> data = new LinkedHashMap<>();

    public boolean hasData(Key key) {
        return data.containsKey(key.getKey());
    }

    public <P> P getData(Key key) {
        return key.<P>getClazz().cast(data.get(key.getKey()));
    }

    public <P> P getDataOrDefault(Key key, P defaultValue) {
        return key.<P>getClazz().cast(data.getOrDefault(key.getKey(), defaultValue));
    }

    public GenericData setData(Key key, Object value) {
        data.put(key.getKey(), key.getClazz().cast(value));
        return this;
    }

    public GenericData setDataIfAbsent(Key key, Object value) {
        data.putIfAbsent(key.getKey(), key.getClazz().cast(value));
        return this;
    }

    public GenericData setDataIfAbsent(Key key, Supplier<?> valueSupplier) {
        data.computeIfAbsent(key.getKey(), k -> key.getClazz().cast(valueSupplier.get()));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> GenericData updateData(Key key, Function<P, P> valueUpdater) {
        data.computeIfPresent(key.getKey(), (k, v) -> key.getClazz().cast(valueUpdater.apply((P) v)));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> GenericData setOrUpdateData(Key key, Function<P, P> valueSetter) {
        data.compute(key.getKey(), (k, v) -> key.getClazz().cast(valueSetter.apply((P) v)));
        return this;
    }

    public GenericData resetData(Key key) {
        data.remove(key.getKey());
        return this;
    }

    public GenericData copyFromParameter(Context<?> context, Key key) {
        setOrUpdateData(key, v -> context.getArgument(key));
        return this;
    }

    public GenericData copyToParameter(Context<?> context, Key key) {
        context.setOrUpdateArgument(key, v -> getData(key));
        return this;
    }
}
