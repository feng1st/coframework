package io.codeone.framework.plugin;

import io.codeone.framework.plugin.util.MethodWrap;

public interface Plugin<T> {

    default T roundBefore(MethodWrap methodWrap, Object[] args)
            throws Throwable {
        before(methodWrap, args);
        return null;
    }

    default void before(MethodWrap methodWrap, Object[] args)
            throws Throwable {
    }

    default Object after(MethodWrap methodWrap, Object[] args,
                         Object result, Throwable error,
                         T before) throws Throwable {
        if (error != null) {
            return afterThrowing(methodWrap, args, error, before);
        }
        return afterReturning(methodWrap, args, result, before);
    }

    default Object afterThrowing(MethodWrap methodWrap, Object[] args,
                                 Throwable error, T before) throws Throwable {
        return afterThrowing(methodWrap, args, error);
    }

    default Object afterThrowing(MethodWrap methodWrap, Object[] args,
                                 Throwable error) throws Throwable {
        throw error;
    }

    default Object afterReturning(MethodWrap methodWrap, Object[] args,
                                  Object result, T before) throws Throwable {
        return afterReturning(methodWrap, args, result);
    }

    default Object afterReturning(MethodWrap methodWrap, Object[] args,
                                  Object result) throws Throwable {
        return result;
    }
}
