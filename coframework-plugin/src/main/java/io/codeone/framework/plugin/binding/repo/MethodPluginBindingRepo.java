package io.codeone.framework.plugin.binding.repo;

import io.codeone.framework.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Repository interface for managing method-to-plugin bindings.
 *
 * <p>This repository provides functionality to dynamically bind plugins to methods
 * and retrieve associated plugins for specific methods.
 */
public interface MethodPluginBindingRepo {

    /**
     * Dynamically binds plugins to a method based on its annotations.
     *
     * @param method the method to bind plugins to
     * @return {@code true} if plugins were successfully bound, {@code false} otherwise
     */
    boolean dynamicBind(Method method);

    /**
     * Retrieves the set of plugin classes bound to a specific method.
     *
     * @param method the method to query
     * @return a set of plugin classes associated with the method
     */
    Set<Class<? extends Plugin>> getPluginClasses(Method method);
}
