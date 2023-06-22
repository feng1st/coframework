package io.codeone.framework.intercept.util;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SignatureFactory {

    private static final Map<Method, Signature> CACHE
            = new ConcurrentHashMap<>();

    public static Signature get(Method method) {
        return CACHE.computeIfAbsent(method, Signature::new);
    }
}