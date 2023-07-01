package io.codeone.framework.plugin.util;

import io.codeone.framework.plugin.Plugin;

/**
 * Interception stores a working-in-progress interception, including the plugin
 * and the value(s) returned by the 'before' method of that plugin.
 * <p>
 * Working-in-progress interceptions are those whose 'before' methods have been
 * executed, and their 'after' methods will be executed in the reverse order.
 *
 * @param <T> The type of the value(s) returned by the 'before' method of the
 *            plugin.
 */
public class Interception<T> {

    private final Plugin<T> plugin;

    private T before;

    public Interception(Plugin<T> plugin) {
        this.plugin = plugin;
    }

    /**
     * Executes the 'before' method of the plugin and stores the intermediate
     * value(s).
     */
    public void before(Context context) throws Throwable {
        before = plugin.aroundBefore(
                context.getMethodWrap(), context.getArgs());
    }

    /**
     * Executes the 'after' method of the plugin, replaces (optionally) and
     * passes the result or exception through the context, so that every
     * working-in-progress plugins in the chain have a chance to handle the
     * result or exception.
     */
    public void after(Context context) {
        try {
            context.setResult(plugin.after(
                    context.getMethodWrap(), context.getArgs(),
                    context.getResult(), context.getError(),
                    before));
        } catch (Throwable t) {
            context.setError(t);
        }
    }
}
