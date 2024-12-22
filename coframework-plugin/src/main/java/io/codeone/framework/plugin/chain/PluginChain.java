package io.codeone.framework.plugin.chain;

import io.codeone.framework.common.function.Invokable;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Represents a chain of plugins that can intercept and process method invocations.
 */
public class PluginChain {

    private final List<Plugin> plugins;

    /**
     * Constructs a new {@code PluginChain}, sorting the provided plugins based on
     * their {@link Plug} stage and {@link org.springframework.core.annotation.Order}.
     *
     * @param plugins the list of plugins to include in the chain
     * @throws NullPointerException if the provided list is {@code null}
     */
    public PluginChain(List<Plugin> plugins) {
        Objects.requireNonNull(plugins);
        this.plugins = sortPlugins(plugins);
    }

    /**
     * Invokes the plugin chain for the given method and arguments.
     *
     * @param method    the method being intercepted
     * @param args      the arguments passed to the method
     * @param invokable the original method invocation
     * @return the result of the invocation
     * @throws Throwable if any plugin or the method itself throws an error
     */
    public Object invoke(Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        return invoke(plugins.iterator(), method, args, invokable);
    }

    private Object invoke(Iterator<Plugin> iter, Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        if (!iter.hasNext()) {
            return invokable.invoke();
        }
        return iter.next().around(method, args, () -> invoke(iter, method, args, invokable));
    }

    private List<Plugin> sortPlugins(List<Plugin> plugins) {
        List<Plugin> result = new LinkedList<>(plugins);
        result.sort(Comparator
                .comparing(o -> o.getClass().getAnnotation(Plug.class).value().getOrder())
                .thenComparing(o -> Optional.ofNullable(o.getClass().getAnnotation(Order.class))
                        .map(Order::value)
                        .orElse(0)));
        return result;
    }
}
