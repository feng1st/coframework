package io.codeone.framework.plugin.function;

@FunctionalInterface
public interface Invokable<T> {

    T invoke() throws Throwable;
}
