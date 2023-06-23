package io.codeone.framework.intercept.util;

/**
 * Represents an invokable function.
 *
 * @param <T> The return type of this invokable.
 */
@FunctionalInterface
public interface Invokable<T> {

    /**
     * Returns a result or throws an exception.
     */
    T invoke() throws Throwable;
}
