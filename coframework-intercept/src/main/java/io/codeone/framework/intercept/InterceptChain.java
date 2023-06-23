package io.codeone.framework.intercept;

import io.codeone.framework.intercept.util.Invokable;

import java.lang.reflect.Method;

public interface InterceptChain {

    Object intercept(Method method, Object[] args, Invokable<Object> invokable)
            throws Throwable;
}
