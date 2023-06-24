package io.codeone.framework.api.aspect;

import io.codeone.framework.api.API;
import io.codeone.framework.api.factory.ApiPluginChainFactory;
import io.codeone.framework.plugin.util.PluginChain;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Order(0)
@Component
public class ApiAspect {

    @Resource
    private ApiPluginChainFactory apiPluginChainFactory;

    @Around("@within(api)"
            + " && !@annotation(io.codeone.framework.api.API)")
    public Object aroundClass(ProceedingJoinPoint pjp, API api)
            throws Throwable {
        return around(pjp, api, true);
    }

    @Around("@annotation(api)")
    public Object aroundMethod(ProceedingJoinPoint pjp, API api)
            throws Throwable {
        return around(pjp, api, false);
    }

    private Object around(ProceedingJoinPoint pjp, API api,
                          boolean atClassLevel) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        PluginChain pluginChain;
        if (api.extraPlugins().length == 0) {
            pluginChain = apiPluginChainFactory.getDefaultChain();
        } else if (atClassLevel) {
            pluginChain = apiPluginChainFactory.getChainOfClass(
                    method.getDeclaringClass(), api.extraPlugins());
        } else {
            pluginChain = apiPluginChainFactory.getChainOfMethod(
                    method, api.extraPlugins());
        }
        return pluginChain.intercept(method, pjp.getArgs(), pjp::proceed);
    }
}
