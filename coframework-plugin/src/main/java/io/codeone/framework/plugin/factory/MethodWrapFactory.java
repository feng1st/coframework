package io.codeone.framework.plugin.factory;

import io.codeone.framework.plugin.util.MethodWrap;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MethodWrapFactory {

    private static final Map<Method, MethodWrap> CACHE
            = new ConcurrentHashMap<>();

    public static MethodWrap get(Method method) {
        return CACHE.computeIfAbsent(method, MethodWrap::new);
    }
}