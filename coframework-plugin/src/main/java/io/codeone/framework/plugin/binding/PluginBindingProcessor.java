package io.codeone.framework.plugin.binding;

import java.lang.reflect.Method;

/**
 * Interface for processing plugin bindings after they have been established.
 */
public interface PluginBindingProcessor {

    /**
     * Processes a method after it has been bound to a plugin.
     *
     * @param method      the method being processed
     * @param targetClass the target class containing the method
     */
    void processAfterBinding(Method method, Class<?> targetClass);
}
