package io.codeone.framework.plugin.plug;

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
        List<Plugging> pluggingList = getPluggingList(method);
        return pluggingList != null && !pluggingList.isEmpty();
    }

    /**
     * Returns what plugins are plugged into the method.
     */
    List<Plugging> getPluggingList(Method method);
}
