package io.codeone.framework.plugin.util;

@FunctionalInterface
public interface Invokable<T> {

    T invoke() throws Throwable;
}
