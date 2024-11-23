package io.codeone.framework.plugin.chain;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.Invokable;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.*;

public class PluginChain {

    private final List<Plugin> plugins;

    public PluginChain(List<Plugin> plugins) {
        Objects.requireNonNull(plugins);
        this.plugins = sortPlugins(plugins);
    }

    public Object invoke(Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        if (plugins.isEmpty()) {
            return invokable.invoke();
        }
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
