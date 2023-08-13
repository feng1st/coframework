package io.codeone.framework.plugin.util;

/**
 * Represents an invokable function.
 *
 * @param <T> the return type of this invokable
 */
@FunctionalInterface
public interface Invokable<T> {

    /**
     * Executes this invokable, and returns a result or throws an exception.
     *
     * @return the result of this invokable
     * @throws Throwable any exception this invokable may throw
     */
    T invoke() throws Throwable;
}
