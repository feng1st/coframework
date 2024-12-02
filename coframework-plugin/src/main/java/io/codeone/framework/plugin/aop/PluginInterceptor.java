package io.codeone.framework.plugin.aop;

import io.codeone.framework.plugin.chain.PluginChain;
import io.codeone.framework.plugin.chain.PluginChainFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Method interceptor that applies plugin chains to intercepted method invocations.
 */
@Component
public class PluginInterceptor implements MethodInterceptor {

    @Autowired
    private PluginChainFactory pluginChainFactory;

    /**
     * Intercepts a method invocation and applies the associated plugin chain, if
     * available.
     *
     * @param invocation the method invocation being intercepted
     * @return the result of the method invocation, possibly modified by plugins
     * @throws Throwable if any plugin or the method invocation itself throws an
     *                   exception
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (invocation.getThis() == null) {
            return invocation.proceed();
        }
        Method method = invocation.getMethod();

        PluginChain pluginChain = pluginChainFactory.getChain(method);
        if (pluginChain == null) {
            return invocation.proceed();
        }
        return pluginChain.invoke(method, invocation.getArguments(), invocation::proceed);
    }
}
