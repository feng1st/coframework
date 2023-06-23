package io.codeone.framework.plugin.util;

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