package io.codeone.framework.plugin;

import io.codeone.framework.common.function.Invokable;

import java.lang.reflect.Method;

/**
 * Represents a plugin interface that allows interception of method invocations.
 *
 * <p>Plugins can define custom behavior before and after a method execution, as
 * well as handle results or exceptions thrown during the method invocation.
 */
public interface Plugin {

    /**
     * Intercepts the method invocation by wrapping it with pre-execution and post-execution
     * hooks.
     *
     * @param method    the method being invoked
     * @param args      the arguments passed to the method
     * @param invokable the invocation handler for the target method
     * @return the result of the method invocation
     * @throws Throwable if any exception occurs during the method execution or
     *                   in the hooks
     */
    default Object around(Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        Object result = null;
        Throwable throwable = null;
        try {
            before(method, args);
            result = invokable.invoke();
        } catch (Throwable t) {
            throwable = t;
        }
        return after(method, args, result, throwable);
    }

    /**
     * Invoked before the target method execution.
     *
     * @param method the method being invoked
     * @param args   the arguments passed to the method
     */
    default void before(Method method, Object[] args) {
    }

    /**
     * Handles the result or exception after the target method execution.
     *
     * <p>Delegates to {@link #afterReturning(Method, Object[], Object)} or {@link
     * #afterThrowing(Method, Object[], Throwable)} based on the presence of an
     * exception.
     *
     * @param method    the method being invoked
     * @param args      the arguments passed to the method
     * @param result    the result of the method invocation, or {@code null} if
     *                  an exception occurred
     * @param throwable the exception thrown during method invocation, or {@code
     *                  null} if no exception occurred
     * @return the final result of the method invocation
     * @throws Throwable if re-throwing exceptions or handling results
     */
    default Object after(Method method, Object[] args, Object result, Throwable throwable)
            throws Throwable {
        if (throwable != null) {
            return afterThrowing(method, args, throwable);
        }
        return afterReturning(method, args, result);
    }

    /**
     * Handles exceptions thrown during the target method execution.
     *
     * <p>By default, rethrows the exception.
     *
     * @param method    the method being invoked
     * @param args      the arguments passed to the method
     * @param throwable the exception thrown during method invocation
     * @return the final result or a custom result
     * @throws Throwable the exception to be propagated
     */
    default Object afterThrowing(Method method, Object[] args, Throwable throwable)
            throws Throwable {
        throw throwable;
    }

    /**
     * Handles the result of a successful method execution.
     *
     * @param method the method being invoked
     * @param args   the arguments passed to the method
     * @param result the result of the method invocation
     * @return the final result of the method invocation
     * @throws Throwable if any post-processing fails
     */
    default Object afterReturning(Method method, Object[] args, Object result)
            throws Throwable {
        return result;
    }
}
