package io.codeone.framework.plugin.chain;

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

    /**
     * Constructs a plugin chain by specifying its contained plugins. Plugins
     * will be sorted by {@link Plug#value()} then by {@link Order} here.
     *
     * @param plugins all plugins this chain contains
     */
    public PluginChain(List<Plugin> plugins) {
        Objects.requireNonNull(plugins);
        this.plugins = plugins;
        sortPlugins();
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
        return PluginChainContext.invoke(() ->
                invoke(plugins.iterator(), TargetMethod.of(method), args, invokable));
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

    private void sortPlugins() {
        plugins.sort(Comparator.comparing(o
                -> Optional.ofNullable(o.getClass().getAnnotation(Plug.class))
                .map(Plug::value)
                .map(Stages::getOrder)
                .orElse(0)).thenComparing(o
                -> Optional.ofNullable(o.getClass().getAnnotation(Order.class))
                .map(Order::value)
                .orElse(0)));
    }
}
