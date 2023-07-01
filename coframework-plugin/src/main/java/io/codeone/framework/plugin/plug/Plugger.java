package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.List;

/**
 * A Plugger is the one who decides whether a method is plugged and what
 * plugins are plugged in.
 */
public interface Plugger {

    /**
     * Returns whether the method is plugged.
     */
    boolean isPlugged(Method method);

    /**
     * Returns what plugins are plugged into the method.
     */
    List<Plugin<?>> getPlugins(Method method);
}
