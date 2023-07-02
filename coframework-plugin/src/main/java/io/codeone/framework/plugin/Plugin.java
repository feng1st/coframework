package io.codeone.framework.plugin;

import io.codeone.framework.plugin.util.MethodWrap;

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
 * <p>
 * There is also a mechanism to pass value(s) between the 'before' and the
 * 'after' methods in one plugin: Anything returned by the 'before' method,
 * will be passed as a parameter to the 'after' method.
 *
 * @param <T> The type of the variable that passed between the 'before' and the
 *            'after' methods.
 */
public interface Plugin<T> {

    /**
     * Intercepts before the invocation of the target.
     * <p>
     * In here you can manipulate the args, the environment, etc. Or throw an
     * exception to break the invocation of the target and other plugins in
     * this chain earlier.
     * <p>
     * Returns anything that you want to pass to the 'after' method of this
     * plugin.
     */
    default T aroundBefore(MethodWrap methodWrap, Object[] args)
            throws Throwable {
        before(methodWrap, args);
        return null;
    }

    /**
     * Intercepts before the invocation of the target.
     * <p>
     * Is the same to 'aroundBefore', except does not handle the
     * variable-passing, i.e. no return value.
     */
    default void before(MethodWrap methodWrap, Object[] args)
            throws Throwable {
    }

    /**
     * Intercepts after the invocation of the target.
     * <p>
     * In here you can handle the value returned by, or the exception thrown by
     * the target or other plugins in this chain. The return value or exception
     * thrown will be replaced if you return or throw a new one.
     * <p>
     * The value(s) returned by the 'before' method will be passed in as the
     * 'before' parameter.
     */
    default Object after(MethodWrap methodWrap, Object[] args, Object result, Throwable error, T before)
            throws Throwable {
        if (error != null) {
            return afterThrowing(methodWrap, args, error, before);
        }
        return afterReturning(methodWrap, args, result, before);
    }

    /**
     * Intercepts after the invocation of the target, if an exception has been
     * thrown.
     * <p>
     * The thrown exception will be replaced if you return a new value or throw
     * a new exception here.
     * <p>
     * The value(s) returned by the 'before' method will be passed in as the
     * 'before' parameter.
     */
    default Object afterThrowing(MethodWrap methodWrap, Object[] args, Throwable error, T before)
            throws Throwable {
        return afterThrowing(methodWrap, args, error);
    }

    /**
     * Intercepts after the invocation of the target, if an exception has been
     * thrown.
     * <p>
     * Is the same to the other 'afterThrowing', except omits the value
     * returned by the 'before' method.
     */
    default Object afterThrowing(MethodWrap methodWrap, Object[] args, Throwable error)
            throws Throwable {
        throw error;
    }

    /**
     * Intercepts after the invocation of the target, if there is no exception.
     * <p>
     * The returned value will be replaced if you return a new value or throw a
     * new exception here.
     * <p>
     * The value(s) returned by the 'before' method will be passed in as the
     * 'before' parameter.
     */
    default Object afterReturning(MethodWrap methodWrap, Object[] args, Object result, T before)
            throws Throwable {
        return afterReturning(methodWrap, args, result);
    }

    /**
     * Intercepts after the invocation of the target, if there is no exception.
     * <p>
     * Is the same to the other 'afterReturning', except omits the value
     * returned by the 'before' method.
     */
    default Object afterReturning(MethodWrap methodWrap, Object[] args, Object result)
            throws Throwable {
        return result;
    }
}
