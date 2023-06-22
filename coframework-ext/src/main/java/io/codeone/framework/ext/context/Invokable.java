package io.codeone.framework.ext.context;

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
