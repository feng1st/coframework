package io.codeone.framework.plugin;

import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.TargetMethod;

/**
 * A plugin is where you can intercept the invocation of a method.
 * <p>
 * Methods of plugins in a chain are executed in a FIFO order: The 'before'
 * method of the first plugin will be executed first, and the 'after' method of
 * this plugin will be executed last.
 * <p>
 * The target, i.e. the method being intercepted will be executed after all
 * 'before' methods of plugins in the plugin chain, and before all 'after'
 * methods of them.
 * <p>
 * If the 'before' method of one plugin throws, plugins after it in the chain,
 * both its 'before' and 'after' methods, will not be executed, as well as the
 * target. On the other hand, the 'after' method of this plugin, and of those
 * plugins before it in the chain, will always be executed.
 */
public interface Plugin {

    /**
     * Intercepts the invocation of the target, by executing the 'before()' and
     * 'after()' methods around the target.
     */
    default Object around(TargetMethod targetMethod, Object[] args, Invokable<?> invokable)
            throws Throwable {
        Object result = null;
        Throwable error = null;
        try {
            before(targetMethod, args);
            result = invokable.invoke();
        } catch (Throwable t) {
            error = t;
        }
        return after(targetMethod, args, result, error);
    }

    /**
     * Intercepts before the invocation of the target.
     * <p>
     * In here you can manipulate the args, the environment, etc. Or throw an
     * exception to break the invocation of the target and other plugins in
     * this chain earlier.
     */
    default void before(TargetMethod targetMethod, Object[] args) {
    }

    /**
     * Intercepts after the invocation of the target.
     * <p>
     * In here you can handle the value returned by, or the exception thrown by
     * the target or other plugins in this chain. The return value or exception
     * thrown will be replaced if you return or throw a new one.
     */
    default Object after(TargetMethod targetMethod, Object[] args, Object result, Throwable error)
            throws Throwable {
        if (error != null) {
            return afterThrowing(targetMethod, args, error);
        }
        return afterReturning(targetMethod, args, result);
    }

    /**
     * Intercepts after the invocation of the target, if an exception has been
     * thrown.
     * <p>
     * The thrown exception will be replaced if you return a new value or throw
     * a new exception here.
     */
    default Object afterThrowing(TargetMethod targetMethod, Object[] args, Throwable error)
            throws Throwable {
        throw error;
    }

    /**
     * Intercepts after the invocation of the target, if there is no exception.
     * <p>
     * The returned value will be replaced if you return a new value or throw a
     * new exception here.
     */
    default Object afterReturning(TargetMethod targetMethod, Object[] args, Object result)
            throws Throwable {
        return result;
    }
}
