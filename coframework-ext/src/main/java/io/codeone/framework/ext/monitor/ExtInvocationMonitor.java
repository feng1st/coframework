package io.codeone.framework.ext.monitor;

/**
 * Monitors the invocation of an Extension method.
 */
public interface ExtInvocationMonitor {

    /**
     * Monitors the invocation of an Extension method.
     *
     * @param extInvocation the invocation of an Extension method
     */
    void monitor(ExtInvocation extInvocation);
}
