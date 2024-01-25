package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.plugin.DefaultPlugger;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Decides whether a method should be intercepted and what plugins should be
 * used for the interception. You can use {@code MethodPlugger} to customize how
 * a batch of plugins to be enabled for target methods. For example,
 * {@link DefaultPlugger} makes {@link EnablePlugin} annotation a
 * plugins-by-classes-enabler, and in {@code coframework-core}
 * {@code ApiPlugger} makes {@code API} annotation an API plugins enabler.
 *
 * <p>To add a {@code MethodPlugger}, all you need to do is to create a subclass
 * of this interface, and make it a spring component. It will be registered and
 * take effect (composed in {@link MethodPluggers}) automatically.
 */
public interface MethodPlugger {

    /**
     * Returns whether the target method should be intercepted by plugins
     * which specified by the {@link Plugging} list that returned by
     * {@link #getPluggingList(Method)}. The default implementation is also
     * based on {@code getPluggingList(Method)}: Returns {@code true} if the
     * {@code Plugging} list of the given method is not empty, otherwise
     * {@code false}.
     *
     * @param method the target method is being tested
     * @return {@code true} if the method should be intercepted by plugins which
     * specified by the {@code Plugging} list that returned by
     * {@code getPluggingList(Method)}
     */
    default boolean isPlugged(Method method) {
        List<Plugging> pluggingList = getPluggingList(method);
        return pluggingList != null && !pluggingList.isEmpty();
    }

    /**
     * Returns a list of {@link Plugging} that defines what plugins should be
     * plugged into the target method.
     *
     * @param method the target method is being tested
     * @return list of {@code Plugging} which defines what plugins should be
     * plugged into the target method
     */
    List<Plugging> getPluggingList(Method method);
}
