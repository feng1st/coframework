package io.codeone.framework.plugin.aop;

import io.codeone.framework.plugin.chain.PluginChain;
import io.codeone.framework.plugin.chain.PluginChainFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
public class PluginInterceptor implements MethodInterceptor {

    @Autowired
    private PluginChainFactory pluginChainFactory;

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
