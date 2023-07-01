package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.List;

/**
 * MethodPlugger decides whether a method is plugged and what plugins are
 * plugged in.
 */
public interface MethodPlugger {

    /**
     * Returns whether the method is plugged.
     */
    default boolean isPlugged(Method method) {
        List<Plugin<?>> plugins = getPlugins(method);
        return plugins != null && !plugins.isEmpty();
    }

    /**
     * Returns what plugins are plugged into the method.
     */
    List<Plugin<?>> getPlugins(Method method);
}
