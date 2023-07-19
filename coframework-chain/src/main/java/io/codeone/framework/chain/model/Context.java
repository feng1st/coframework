package io.codeone.framework.chain.model;

import io.codeone.framework.chain.constants.Key;
import io.codeone.framework.chain.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@AllArgsConstructor(staticName = "of")
public class Context<T> {

    @Getter
    @Setter
    private T target;

    private final Map<Key, Object> argumentsByKey = new LinkedHashMap<>();

    private final Map<Class<?>, Object> argumentsByClass = new LinkedHashMap<>();

    public boolean hasArgument(Key key) {
        return argumentsByKey.containsKey(key);
    }

    public boolean hasArgument(Class<?> clazz) {
        return argumentsByClass.containsKey(clazz);
    }

    public <P> P getArgument(Key key) {
        return key.<P>getClazz().cast(argumentsByKey.get(key));
    }

    @SuppressWarnings("unchecked")
    public <P> P getArgument(Class<?> clazz) {
        return (P) argumentsByClass.get(clazz);
    }

    public <P> P getArgumentOrDefault(Key key, P defaultValue) {
        return key.<P>getClazz().cast(argumentsByKey.getOrDefault(key, defaultValue));
    }

    @SuppressWarnings("unchecked")
    public <P> P getArgumentOrDefault(Class<?> clazz, P defaultValue) {
        return (P) argumentsByClass.getOrDefault(clazz, defaultValue);
    }

    public Context<T> setArgument(Key key, Object value) {
        argumentsByKey.put(key, key.getClazz().cast(value));
        return this;
    }

    public Context<T> setArgument(Object value) {
        argumentsByClass.put(value.getClass(), value);
        return this;
    }

    public Context<T> setArgumentIfAbsent(Key key, Object value) {
        argumentsByKey.putIfAbsent(key, key.getClazz().cast(value));
        return this;
    }

    public Context<T> setArgumentIfAbsent(Object value) {
        argumentsByClass.putIfAbsent(value.getClass(), value);
        return this;
    }

    public Context<T> setArgumentIfAbsent(Key key, Supplier<?> valueSupplier) {
        argumentsByKey.computeIfAbsent(key, k -> key.getClazz().cast(valueSupplier.get()));
        return this;
    }

    public <P> Context<T> setArgumentIfAbsent(Class<P> clazz, Supplier<P> valueSupplier) {
        argumentsByClass.computeIfAbsent(clazz, k -> valueSupplier.get());
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> Context<T> updateArgument(Key key, Function<P, P> valueUpdater) {
        argumentsByKey.computeIfPresent(key, (k, v) -> key.getClazz().cast(valueUpdater.apply((P) v)));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> Context<T> updateArgument(Class<P> clazz, Function<P, P> valueUpdater) {
        argumentsByClass.computeIfPresent(clazz, (k, v) -> valueUpdater.apply((P) v));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> Context<T> setOrUpdateArgument(Key key, Function<P, P> valueSetter) {
        argumentsByKey.compute(key, (k, v) -> key.getClazz().cast(valueSetter.apply((P) v)));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> Context<T> setOrUpdateArgument(Class<P> clazz, Function<P, P> valueSetter) {
        argumentsByClass.compute(clazz, (k, v) -> valueSetter.apply((P) v));
        return this;
    }

    public Context<T> resetArgument(Key key) {
        argumentsByKey.remove(key);
        return this;
    }

    public Context<T> resetArgument(Class<?> clazz) {
        argumentsByClass.remove(clazz);
        return this;
    }

    public void log(Logger logger) {
        logger.logTarget(target);
        argumentsByKey.forEach(logger::log);
        argumentsByClass.forEach(logger::log);
    }
}
