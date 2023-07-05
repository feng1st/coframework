package io.codeone.framework.plugin.chain;

import io.codeone.framework.plugin.util.Invokable;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@UtilityClass
@SuppressWarnings("unchecked")
public class PluginChainContext {

    private static final ThreadLocal<Map<String, Object>> MAP = ThreadLocal.withInitial(HashMap::new);

    static Object invoke(Invokable<?> invokable) throws Throwable {
        try {
            return invokable.invoke();
        } finally {
            MAP.remove();
        }
    }

    public static boolean containsKey(String key) {
        return MAP.get().containsKey(key);
    }

    public static <T> T get(String key) {
        return (T) MAP.get().get(key);
    }

    public static <T> T put(String key, T value) {
        return (T) MAP.get().put(key, value);
    }

    public static <T> T remove(String key) {
        return (T) MAP.get().remove(key);
    }

    public static <T> T getOrDefault(String key, T defaultValue) {
        return (T) MAP.get().getOrDefault(key, defaultValue);
    }

    public static <T> T putIfAbsent(String key, T value) {
        return (T) MAP.get().putIfAbsent(key, value);
    }

    public static <T> boolean remove(String key, T value) {
        return MAP.get().remove(key, value);
    }

    public static <T> boolean replace(String key, T oldValue, T newValue) {
        return MAP.get().replace(key, oldValue, newValue);
    }

    public static <T> T replace(String key, T value) {
        return (T) MAP.get().replace(key, value);
    }

    public static <T> T computeIfAbsent(String key, Function<? super String, T> mappingFunction) {
        return (T) MAP.get().computeIfAbsent(key, mappingFunction);
    }
}
