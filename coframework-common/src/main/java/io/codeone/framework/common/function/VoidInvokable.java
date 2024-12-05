package io.codeone.framework.common.function;

/**
 * Represents a callable function that has no return value and may throw exceptions.
 *
 * <p>This functional interface is useful for encapsulating potentially exception-throwing
 * logic that does not produce a result.
 */
@FunctionalInterface
public interface VoidInvokable {

    /**
     * Executes the function.
     *
     * @throws Throwable if an error occurs during execution
     */
    void invoke() throws Throwable;
}
