package io.codeone.framework.plugin.aspect;

import io.codeone.framework.api.API;
import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.chain.PluginChain;
import io.codeone.framework.plugin.factory.MethodWrapFactory;
import io.codeone.framework.plugin.factory.PluginChainFactory;
import io.codeone.framework.plugin.util.MethodWrap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

// FIXME
@Aspect
@Order(0)
@Component
public class PluginAspect {

    @Resource
    private PluginChainFactory pluginChainFactory;

    @Around("@within(io.codeone.framework.api.API)"
            + " || @annotation(io.codeone.framework.api.API)"
            + " || @within(io.codeone.framework.plugin.EnablePlugin)"
            + " || @annotation(io.codeone.framework.plugin.EnablePlugin)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        PluginChain pluginChain = getChain(method);
        if (pluginChain == null) {
            return pjp.proceed();
        }
        return pluginChain.intercept(method, pjp.getArgs(), pjp::proceed);
    }

    public PluginChain getChain(Method method) {
        MethodWrap methodWrap = MethodWrapFactory.get(method);

        boolean isApi = methodWrap.isAnnotationPresent(API.class);
        EnablePlugin enablePlugin;
        if ((enablePlugin = methodWrap.getMethodAnnotation(EnablePlugin.class))
                != null) {
            return pluginChainFactory.getPluginChainOfMethod(
                    methodWrap.getMethod(),
                    enablePlugin.value(), isApi);
        }
        if ((enablePlugin = methodWrap.getClassAnnotation(EnablePlugin.class))
                != null) {
            return pluginChainFactory.getPluginChainOfClass(
                    methodWrap.getDeclaringClass(),
                    enablePlugin.value(), isApi);
        }
        if (isApi) {
            return pluginChainFactory.getApiPluginChain();
        }
        return null;
    }
}
