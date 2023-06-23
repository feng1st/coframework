package io.codeone.framework.plugin.util;

import io.codeone.framework.plugin.Plugin;

public class Interception<T> {

    private final Plugin<T> plugin;

    private T before;

    public Interception(Plugin<T> plugin) {
        this.plugin = plugin;
    }

    public void before(Context context) throws Throwable {
        before = plugin.roundBefore(
                context.getMethodWrap(), context.getArgs());
    }

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
