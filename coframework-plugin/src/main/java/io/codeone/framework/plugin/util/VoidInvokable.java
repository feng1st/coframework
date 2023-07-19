package io.codeone.framework.plugin.util;

/**
 * Represents an invokable function.
 */
@FunctionalInterface
public interface VoidInvokable {

    /**
     * Invokes without returning, may throw an exception.
     */
    void invoke() throws Throwable;
}
