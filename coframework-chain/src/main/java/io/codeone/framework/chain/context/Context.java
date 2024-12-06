package io.codeone.framework.chain.context;

import io.codeone.framework.chain.log.MDC;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Accessors(fluent = true, chain = true)
@Slf4j(topic = "chain")
public class Context implements BizScenarioParam {

    private final Map<Object, Object> paramMap = new ConcurrentHashMap<>();

    @Getter
    @Setter
    private String chainName = "anonymous";

    @Getter
    @Setter
    private BizScenario bizScenario;

    @Getter
    @Setter
    private ExecutorService threadPool;

    @Getter
    @Setter
    private Consumer<Context> onExecute;

    public static Context of() {
        return new Context();
    }

    public static Context of(Object k1, Object v1) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        return context;
    }

    public static Context of(Object k1, Object v1, Object k2, Object v2) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        return context;
    }

    public static Context of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        context.paramMap.put(k3, v3);
        return context;
    }

    public static Context of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        context.paramMap.put(k3, v3);
        context.paramMap.put(k4, v4);
        return context;
    }

    public static Context of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4,
                             Object k5, Object v5) {
        Context context = new Context();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        context.paramMap.put(k3, v3);
        context.paramMap.put(k4, v4);
        context.paramMap.put(k5, v5);
        return context;
    }

    public static Context of(Map<Object, Object> paramMap) {
        Context context = new Context();
        context.paramMap.putAll(paramMap);
        return context;
    }

    public static Context of(Context context) {
        return of(context.paramMap);
    }

    public Context with(Object key, Object value) {
        put(key, value);
        return this;
    }

    public <V> void ifPresent(Object key, Consumer<V> action) {
        Optional.ofNullable(this.<V>tryCast(key, paramMap.get(key)))
                .ifPresent(action);
    }

    public <V> V get(Object key) {
        return tryCast(key, paramMap.get(key));
    }

    public <V> V getOrDefault(Object key, V defaultValue) {
        return tryCast(key, paramMap.getOrDefault(key, tryCast(key, defaultValue)));
    }

    public <V> V put(Object key, V value) {
        if (value != null) {
            return tryCast(key, paramMap.put(key, tryCast(key, value)));
        } else {
            return tryCast(key, paramMap.remove(key));
        }
    }

    public <V> V putIfAbsent(Object key, V value) {
        if (value != null) {
            return tryCast(key, paramMap.putIfAbsent(key, tryCast(key, value)));
        }
        return null;
    }

    public <V> V remove(Object key) {
        return tryCast(key, paramMap.remove(key));
    }

    public <V> V computeIfAbsent(Object key, Function<Object, ? extends V> mappingFunction) {
        return tryCast(key, paramMap.computeIfAbsent(key,
                k -> tryCast(k, mappingFunction.apply(k))));
    }

    public <V> V computeIfPresent(Object key, BiFunction<Object, ? super V, ? extends V> remappingFunction) {
        return tryCast(key, paramMap.computeIfPresent(key,
                (k, v) -> tryCast(k, remappingFunction.apply(k, tryCast(k, v)))));
    }

    public <V> V compute(Object key, BiFunction<Object, ? super V, ? extends V> remappingFunction) {
        return tryCast(key, paramMap.compute(key,
                (k, v) -> tryCast(k, remappingFunction.apply(k, tryCast(k, v)))));
    }

    public <K, V> V kvGet(Object key, K subKey) {
        Map<K, V> subMap = get(key);
        if (subMap != null) {
            return subMap.get(subKey);
        }
        return null;
    }

    public <K, V> V kvPut(Object key, K subKey, V value) {
        if (value != null) {
            return computeIfAbsent(key, k -> new ConcurrentHashMap<K, V>())
                    .put(subKey, value);
        }
        Map<K, V> subMap = get(key);
        if (subMap != null) {
            return subMap.remove(subKey);
        }
        return null;
    }

    public <K, V> V kvComputeIfAbsent(Object key, K subKey, Function<? super K, ? extends V> mappingFunction) {
        return computeIfAbsent(key, k -> new ConcurrentHashMap<K, V>())
                .computeIfAbsent(subKey, mappingFunction);
    }

    public void log(Object key, Object value) {
        MDC.put(key, value);
    }

    @Override
    public BizScenario getBizScenario() {
        return bizScenario;
    }

    @SuppressWarnings("unchecked")
    private <T> T tryCast(Object key, Object obj) {
        if (key instanceof Typed) {
            return ((Typed) key).cast(obj);
        }
        return (T) obj;
    }
}
