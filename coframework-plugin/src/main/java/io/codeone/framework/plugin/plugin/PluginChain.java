package io.codeone.framework.plugin.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.TargetMethod;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.*;

/**
 * A chain of plugins which is used to intercept method invocation. Plugins in
 * the chain are sorted by {@link Plug#value()} then by {@link Order}.
 */
public class PluginChain {

    private final List<Plugin> plugins;

    public PluginChain(List<Plugin> plugins) {
        Objects.requireNonNull(plugins);
        this.plugins = sortPlugins(plugins);
    }

    /**
     * Executes this chain on an invokable, for example, a method invocation or
     * a lambda expression that wraps the method invocation.
     *
     * @param method    the target method
     * @param args      the arguments of the method invocation
     * @param invokable the invocation of the target method, or a lambda
     *                  expression wrapping it
     * @return the result of the target method invocation, or an intercepted one
     * by the plugins in the chain
     * @throws Throwable any exception thrown by the target method invocation or
     *                   by the plugins in the chain
     */
    public Object invoke(Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        if (plugins.isEmpty()) {
            return invokable.invoke();
        }
        return invoke(plugins.iterator(), TargetMethod.of(method), args, invokable);
    }

    private Object invoke(Iterator<Plugin> iter, TargetMethod targetMethod, Object[] args, Invokable<?> invokable)
            throws Throwable {
        if (iter.hasNext()) {
            return iter.next().around(targetMethod, args,
                    () -> invoke(iter, targetMethod, args, invokable));
        } else {
            return invokable.invoke();
        }
    }

    private List<Plugin> sortPlugins(List<Plugin> plugins) {
        List<Plugin> result = new LinkedList<>(plugins);
        result.sort(Comparator.comparing(o
                -> Optional.ofNullable(o.getClass().getAnnotation(Plug.class))
                .map(Plug::value)
                .orElse(Stages.DEFAULT)
                .getOrder()).thenComparing(o
                -> Optional.ofNullable(o.getClass().getAnnotation(Order.class))
                .map(Order::value)
                .orElse(0)));
        return result;
    }
}
