package io.codeone.framework.intercept;

import io.codeone.framework.intercept.util.Signature;

public interface Interceptor<T> {

    default T roundBefore(Signature signature, Object[] args)
            throws Throwable {
        before(signature, args);
        return null;
    }

    default void before(Signature signature, Object[] args) throws
            Throwable {
    }

    default Object after(Signature signature, Object[] args,
                         Object result, Throwable error,
                         T before) throws Throwable {
        if (error != null) {
            return afterThrowing(signature, args, error, before);
        }
        return afterReturning(signature, args, result, before);
    }

    default Object afterThrowing(Signature signature, Object[] args,
                                 Throwable error, T before) throws Throwable {
        return afterThrowing(signature, args, error);
    }

    default Object afterThrowing(Signature signature, Object[] args,
                                 Throwable error) throws Throwable {
        throw error;
    }

    default Object afterReturning(Signature signature, Object[] args,
                                  Object result, T before) throws Throwable {
        return afterReturning(signature, args, result);
    }

    default Object afterReturning(Signature signature, Object[] args,
                                  Object result) throws Throwable {
        return result;
    }
}
