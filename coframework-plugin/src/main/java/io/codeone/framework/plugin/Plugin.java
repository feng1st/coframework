package io.codeone.framework.plugin;

import io.codeone.framework.plugin.plug.MethodPlugger;
import io.codeone.framework.plugin.plugin.DefaultPlugger;
import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.TargetMethod;
import org.springframework.core.annotation.Order;

/**
 * A plugin is where you can intercept the invocation of a target method. The
 * implementation of plugins is very similar to AOP.
 *
 * <p>Multiple plugins applied to the same target method form a plugin chain.
 * Methods of plugins in a chain are executed in a FIFO order: The
 * {@code before} method of the first plugin will be executed first, and the
 * {@code after} method of that plugin will be executed last. Please keep this
 * in mind when you use the {@link Plug#value()} and {@link Order} to manage the
 * order of plugins.
 *
 * <p>The target method (the method being intercepted) will be executed after
 * the {@code before} method of the last plugin in the chain, and followed by
 * the {@code after} methods of that plugin.
 *
 * <p>If the {@code before} method of one plugin throws an exception, subsequent
 * plugins in the chain will not be executed, as well as the target method. On
 * the other hand, if the {@code before} method of one plugin has been executed,
 * its {@code after} method will always be executed.
 *
 * <p>You can apply plugins to methods/services via {@code MethodPlugger},
 * please refer to {@link MethodPlugger} for more information. The framework
 * provided a {@link DefaultPlugger} {@code MethodPlugger} which apply
 * individual plugins using the {@link EnablePlugin} annotation. The
 * {@code DefaultPlugger} and {@code EnablePlugin} is also a good example about
 * how to write a custom {@code MethodPlugger} and a plugin enabling annotation.
 *
 * @see EnablePlugin
 * @see MethodPlugger
 * @see Plug
 */
public interface Plugin {

    /**
     * Intercepts the invocation of the target method, by executing the
     * {@code before} and {@code after} methods of this plugin, around the
     * target method.
     *
     * @param targetMethod the signature of the target method
     * @param args         arguments of the target method invocation
     * @param invokable    the target method, or the rest of the plugin chain
     * @return the intercepted result of the target method
     * @throws Throwable exception thrown by the target method or plugins
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
     * Intercepts before the invocation of the target method, for example,
     * manipulates the arguments. If you throw an exception here, the invocation
     * of the subsequent plugins in the chain, and the target method, will be
     * interrupted. The exception you throw will be handled by the {@code after}
     * method of this plugin, or plugins prior to this plugin in the chain, or
     * just be thrown to the caller.
     *
     * @param targetMethod the signature of the target method
     * @param args         arguments of the target method invocation
     */
    default void before(TargetMethod targetMethod, Object[] args) {
    }

    /**
     * Intercepts after the invocation of the target method, by handling the
     * result returned or the exception thrown by the target method, the
     * subsequent (please note the FIFO order) plugins in the chain, or the
     * {@code before} method of this plugin (exception only). The result or the
     * exception will be replaced if you return or throw a new one.
     *
     * @param targetMethod the signature of the target method
     * @param args         arguments of the target method invocation
     * @param result       the result returned by the target method or the
     *                     subsequent plugins in the chain
     * @param error        the exception thrown by the target method, the
     *                     subsequent plugins in the chain, or the
     *                     {@code before} method of this plugin
     * @return result of the target method or plugins (including this one)
     * @throws Throwable exception thrown by the target method or plugins
     *                   (including this one)
     */
    default Object after(TargetMethod targetMethod, Object[] args, Object result, Throwable error)
            throws Throwable {
        if (error != null) {
            return afterThrowing(targetMethod, args, error);
        }
        return afterReturning(targetMethod, args, result);
    }

    /**
     * Intercepts after the invocation of the target method, if and only if an
     * exception has been thrown. The exception may be thrown by the target
     * method, the subsequent (FIFO) plugins in the chain, or the {@code before}
     * method of this plugin. You can throw a new or updated exception to
     * replace the existing exception. And if you return a value instead of
     * throwing an exception, the existing exception will be discarded.
     *
     * @param targetMethod the signature of the target method
     * @param args         arguments of the target method invocation
     * @param error        the exception thrown by the target method, the
     *                     subsequent plugins in the chain, or the
     *                     {@code before} method of this plugin
     * @return result of the target method or plugins (including this one)
     * @throws Throwable exception thrown by the target method or plugins
     *                   (including this one)
     */
    default Object afterThrowing(TargetMethod targetMethod, Object[] args, Throwable error)
            throws Throwable {
        throw error;
    }

    /**
     * Intercepts after the invocation of the target method, if and only if
     * there was no exception. The result value may be returned by the target
     * method, or the subsequent (FIFO) plugins in the chain. You can return a
     * new or updated result, or just throw an exception here.
     *
     * @param targetMethod the signature of the target method
     * @param args         arguments of the target method invocation
     * @param result       the result returned by the target method or the
     *                     subsequent plugins in the chain
     * @return result of the target method or plugins (including this one)
     * @throws Throwable exception thrown by the target method or plugins
     *                   (including this one)
     */
    default Object afterReturning(TargetMethod targetMethod, Object[] args, Object result)
            throws Throwable {
        return result;
    }
}
