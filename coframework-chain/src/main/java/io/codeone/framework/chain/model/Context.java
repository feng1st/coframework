package io.codeone.framework.chain.model;

import io.codeone.framework.chain.logging.ContextLogger;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.model.Key;
import io.codeone.framework.model.KeyMap;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The context of the chain at runtime, including the target the chain operates
 * on, and the input and output arguments of each node.
 *
 * @param <T> The type of the target.
 */
public class Context<T> {

    /**
     * The target the chain manipulates. It could be passed from outside, or
     * generated by one of the nodes.
     */
    @Getter
    @Setter
    private volatile T target;

    /**
     * The logger that logs the context before the execution of the chain.
     */
    private final ContextLogger<T> chainLogger;

    /**
     * The logger that logs the context before the execution of each node.
     */
    private final ContextLogger<T> nodeLogger;

    /**
     * Input and output arguments of the nodes, mapped by Keys.
     */
    private final Map<Key, Object> argumentsByKey = new ConcurrentHashMap<>();

    /**
     * Input and output arguments of the nodes, mapped by their classes.
     */
    private final Map<Class<?>, Object> argumentsByClass = new ConcurrentHashMap<>();

    public static <T> Builder<T> newBuilder() {
        return new Builder<>();
    }

    public static <T> Context<T> ofEmpty() {
        return new Context<>(null, null, null);
    }

    public static <T> Context<T> of(T target) {
        return new Context<>(target, null, null);
    }

    public Context(T target, ContextLogger<T> chainLogger, ContextLogger<T> nodeLogger) {
        this.target = target;
        this.chainLogger = (chainLogger == null) ? Context::logAll : chainLogger;
        this.nodeLogger = nodeLogger;
    }

    public void ifTargetPresent(Consumer<T> consumer) {
        Optional.ofNullable(target)
                .ifPresent(consumer);
    }

    public void ifArgumentPresent(Key key, Consumer<Object> consumer) {
        Optional.ofNullable(argumentsByKey.get(key))
                .ifPresent(consumer);
    }

    public void ifArgumentPresent(Class<?> clazz, Consumer<Object> consumer) {
        Optional.ofNullable(argumentsByClass.get(clazz))
                .ifPresent(consumer);
    }

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
    public <P> Context<T> updateArgumentIfPresent(Key key, Function<P, P> valueUpdater) {
        argumentsByKey.computeIfPresent(key, (k, v) -> key.getClazz().cast(valueUpdater.apply((P) v)));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <P> Context<T> updateArgumentIfPresent(Class<P> clazz, Function<P, P> valueUpdater) {
        argumentsByClass.computeIfPresent(clazz, (k, v) -> valueUpdater.apply((P) v));
        return this;
    }

    /**
     * Sets a new argument if there is no existing one, otherwise updates the
     * existing one.
     *
     * @param valueSetter The function that takes the existing argument as the
     *                    input and output a new one. A null input indicates
     *                    that there was no value associated with this key.
     */
    @SuppressWarnings("unchecked")
    public <P> Context<T> setOrUpdateArgument(Key key, Function<P, P> valueSetter) {
        argumentsByKey.compute(key, (k, v) -> key.getClazz().cast(valueSetter.apply((P) v)));
        return this;
    }

    /**
     * Sets a new argument if there is no existing one, otherwise updates the
     * existing one.
     *
     * @param valueSetter The function that takes the existing argument as the
     *                    input and output a new one. A null input indicates
     *                    that there was no value associated with this class.
     */
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

    /**
     * Copies an argument from the KeyMap.
     */
    public Context<T> copyFrom(KeyMap keyMap, Key key) {
        setOrUpdateArgument(key, v -> keyMap.get(key));
        return this;
    }

    /**
     * Copies an argument to the KeyMap.
     */
    public Context<T> copyTo(KeyMap keyMap, Key key) {
        keyMap.putOrUpdate(key, v -> getArgument(key));
        return this;
    }

    public void logChain(Logger logger) {
        chainLogger.log(this, logger);
    }

    public void logNode(Logger logger) {
        if (nodeLogger != null) {
            nodeLogger.log(this, logger);
        }
    }

    private void logAll(Logger logger) {
        logger.logTarget(target);
        argumentsByKey.forEach(logger::log);
        argumentsByClass.forEach(logger::log);
    }

    @Setter
    @Accessors(fluent = true)
    public static class Builder<T> {
        private T withTarget;
        private ContextLogger<T> withChainLogger;
        private ContextLogger<T> withNodeLogger;

        public Context<T> build() {
            return new Context<>(withTarget, withChainLogger, withNodeLogger);
        }
    }
}
