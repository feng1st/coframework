package io.codeone.framework.plugin;

import io.codeone.framework.plugin.util.Invokable;

import java.lang.reflect.Method;

public interface Plugin {

    default Object around(Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        Object result = null;
        Throwable throwable = null;
        try {
            before(method, args);
            result = invokable.invoke();
        } catch (Throwable t) {
            throwable = t;
        }
        return after(method, args, result, throwable);
    }

    default void before(Method method, Object[] args) {
    }

    default Object after(Method method, Object[] args, Object result, Throwable throwable)
            throws Throwable {
        if (throwable != null) {
            return afterThrowing(method, args, throwable);
        }
        return afterReturning(method, args, result);
    }

    default Object afterThrowing(Method method, Object[] args, Throwable throwable)
            throws Throwable {
        throw throwable;
    }

    default Object afterReturning(Method method, Object[] args, Object result)
            throws Throwable {
        return result;
    }
}
