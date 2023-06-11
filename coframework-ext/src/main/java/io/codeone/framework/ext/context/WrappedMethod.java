package io.codeone.framework.ext.context;

@FunctionalInterface
public interface WrappedMethod<T> {

    T invoke() throws Throwable;
}
