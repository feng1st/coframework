package io.codeone.framework.plugin.chain;

import io.codeone.framework.plugin.util.Invokable;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@UtilityClass
@SuppressWarnings("unchecked")
public class PluginChainContext {

    private final ThreadLocal<Map<String, Object>> MAP = ThreadLocal.withInitial(HashMap::new);

    Object invoke(Invokable<?> invokable) throws Throwable {
        try {
            return invokable.invoke();
        } finally {
            MAP.remove();
        }
    }

    public boolean containsKey(String key) {
        return MAP.get().containsKey(key);
    }

    public <T> T get(String key) {
        return (T) MAP.get().get(key);
    }

    public <T> T put(String key, T value) {
        return (T) MAP.get().put(key, value);
    }

    public <T> T remove(String key) {
        return (T) MAP.get().remove(key);
    }

    public <T> T getOrDefault(String key, T defaultValue) {
        return (T) MAP.get().getOrDefault(key, defaultValue);
    }

    public <T> T putIfAbsent(String key, T value) {
        return (T) MAP.get().putIfAbsent(key, value);
    }

    public <T> boolean remove(String key, T value) {
        return MAP.get().remove(key, value);
    }

    public <T> boolean replace(String key, T oldValue, T newValue) {
        return MAP.get().replace(key, oldValue, newValue);
    }

    public <T> T replace(String key, T value) {
        return (T) MAP.get().replace(key, value);
    }

    public <T> T computeIfAbsent(String key, Function<? super String, T> mappingFunction) {
        return (T) MAP.get().computeIfAbsent(key, mappingFunction);
    }
}
