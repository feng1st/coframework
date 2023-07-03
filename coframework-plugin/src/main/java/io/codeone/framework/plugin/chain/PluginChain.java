package io.codeone.framework.plugin.chain;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.MethodWrap;
import io.codeone.framework.plugin.util.MethodWrapCache;

import java.lang.reflect.Method;
import java.util.*;

/**
 * A chain of sorted plugins.
 */
public class PluginChain {

    private final List<Plugin> plugins;

    public PluginChain(List<Plugin> plugins) {
        Objects.requireNonNull(plugins);
        this.plugins = plugins;
        sortPlugins();
    }

    /**
     * Executes the chain on an invokable.
     */
    public Object invoke(Method method, Object[] args, Invokable<Object> invokable)
            throws Throwable {
        if (plugins.isEmpty()) {
            return invokable.invoke();
        }
        return invoke(plugins.iterator(), MethodWrapCache.get(method), args, invokable);
    }

    private Object invoke(Iterator<Plugin> iter, MethodWrap methodWrap, Object[] args, Invokable<Object> invokable)
            throws Throwable {
        if (iter.hasNext()) {
            return iter.next().around(methodWrap, args,
                    () -> invoke(iter, methodWrap, args, invokable));
        } else {
            return invokable.invoke();
        }
    }

    private void sortPlugins() {
        plugins.sort(Comparator.comparing(o
                -> Optional.ofNullable(o.getClass().getAnnotation(Plug.class))
                .map(Plug::value)
                .map(Stages::getOrder)
                .orElse(Integer.MAX_VALUE)).thenComparing(o
                -> Optional.ofNullable(o.getClass().getAnnotation(Plug.class))
                .map(Plug::order)
                .orElse(Integer.MAX_VALUE)));
    }
}
