package io.codeone.framework.intercept;

import io.codeone.framework.intercept.util.TargetMethod;

public interface Interceptor<T> {

    default T roundBefore(TargetMethod method, Object[] args)
            throws Throwable {
        before(method, args);
        return null;
    }

    default void before(TargetMethod method, Object[] args)
            throws Throwable {
    }

    default Object after(TargetMethod method, Object[] args,
                         Object result, Throwable error,
                         T before) throws Throwable {
        if (error != null) {
            return afterThrowing(method, args, error, before);
        }
        return afterReturning(method, args, result, before);
    }

    default Object afterThrowing(TargetMethod method, Object[] args,
                                 Throwable error, T before) throws Throwable {
        return afterThrowing(method, args, error);
    }

    default Object afterThrowing(TargetMethod method, Object[] args,
                                 Throwable error) throws Throwable {
        throw error;
    }

    default Object afterReturning(TargetMethod method, Object[] args,
                                  Object result, T before) throws Throwable {
        return afterReturning(method, args, result);
    }

    default Object afterReturning(TargetMethod method, Object[] args,
                                  Object result) throws Throwable {
        return result;
    }
}
