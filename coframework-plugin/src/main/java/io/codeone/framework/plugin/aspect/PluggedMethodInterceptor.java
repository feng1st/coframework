package io.codeone.framework.plugin.aspect;

import io.codeone.framework.plugin.factory.PluginChainFactory;
import io.codeone.framework.plugin.util.PluginChain;
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
