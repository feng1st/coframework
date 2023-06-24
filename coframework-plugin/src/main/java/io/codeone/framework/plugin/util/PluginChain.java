package io.codeone.framework.plugin.util;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;

import java.lang.reflect.Method;
import java.util.*;

/**
 * A chain of sorted plugins.
 */
public class PluginChain {

    private final List<? extends Plugin<?>> plugins;

    public PluginChain(List<? extends Plugin<?>> plugins) {
        Objects.requireNonNull(plugins);
        this.plugins = plugins;
        sortPlugins();
    }

    /**
     * Executes the chain on an invokable.
     */
    public Object intercept(Method method, Object[] args,
                            Invokable<Object> invokable) throws Throwable {
        if (plugins.isEmpty()) {
            return invokable.invoke();
        }

        Context context = new Context(method, args);

        LinkedList<Interception<?>> stack = new LinkedList<>();

        try {
            before(stack, context);

            context.setResult(invokable.invoke());
        } catch (Throwable t) {
            context.setError(t);
        }

        after(stack, context);

        return context.getResultOrThrow();
    }

    private void sortPlugins() {
        plugins.sort(Comparator
                .comparing(o -> Optional.ofNullable(o.getClass()
                                .getAnnotation(Plug.class))
                        .map(Plug::value)
                        .map(Stages::getOrder)
                        .orElse(Integer.MAX_VALUE))
                .thenComparing(o -> Optional.ofNullable(o.getClass()
                                .getAnnotation(Plug.class))
                        .map(Plug::order)
                        .orElse(Integer.MAX_VALUE)));
    }

    private void before(LinkedList<Interception<?>> stack, Context context)
            throws Throwable {
        for (Plugin<?> plugin : plugins) {
            Interception<?> interception = new Interception<>(plugin);
            stack.push(interception);
            interception.before(context);
        }
    }

    private void after(LinkedList<Interception<?>> stack, Context context) {
        while (!stack.isEmpty()) {
            stack.pop().after(context);
        }
    }
}
