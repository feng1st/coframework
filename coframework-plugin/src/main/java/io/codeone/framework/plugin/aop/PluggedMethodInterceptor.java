package io.codeone.framework.plugin.aop;

import io.codeone.framework.plugin.chain.PluginChain;
import io.codeone.framework.plugin.chain.PluginChainFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Component
public class PluggedMethodInterceptor implements MethodInterceptor {

    @Resource
    private PluginChainFactory pluginChainFactory;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        PluginChain pluginChain = pluginChainFactory.getChain(method);
        if (pluginChain == null) {
            return invocation.proceed();
        }
        return pluginChain.intercept(method, invocation.getArguments(),
                invocation::proceed);
    }
}
