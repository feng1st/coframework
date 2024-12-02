package io.codeone.framework.plugin.function;

/**
 * Represents a callable function that can return a result and may throw exceptions.
 *
 * <p>This functional interface allows defining lambda expressions or method references
 * that can encapsulate potentially exception-throwing logic with a return value.
 *
 * @param <T> the return type of the function
 */
@FunctionalInterface
public interface Invokable<T> {

    /**
     * Executes the function.
     *
     * @return the result of the function
     * @throws Throwable if an error occurs during execution
     */
    T invoke() throws Throwable;
}
