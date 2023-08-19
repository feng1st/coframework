package io.codeone.framework.plugin.aop;

import io.codeone.framework.plugin.chain.PluginChain;
import io.codeone.framework.plugin.chain.PluginChainFactory;
import io.codeone.framework.plugin.plug.MethodPluggers;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Intercepts method invocation using plugins. Should methods be intercepted is
 * determined by {@link PluggedPointcut}, and what plugins should be used is
 * decided by {@link MethodPluggers} in {@link PluginChainFactory}.
 *
 * <p>{@code PluggedMethodInterceptor} and {@code PluggedPointcut} are assembled
 * together by {@link PluggedPointcutAdvisor}.
 */
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
        return pluginChain.invoke(method, invocation.getArguments(), invocation::proceed);
    }
}
