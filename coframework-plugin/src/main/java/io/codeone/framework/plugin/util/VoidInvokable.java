package io.codeone.framework.plugin.util;

/**
 * Represents an invokable function which does not return a value.
 */
@FunctionalInterface
public interface VoidInvokable {

    /**
     * Executes this invokable, and may throw an exception.
     *
     * @throws Throwable any exception this invokable may throw
     */
    void invoke() throws Throwable;
}
