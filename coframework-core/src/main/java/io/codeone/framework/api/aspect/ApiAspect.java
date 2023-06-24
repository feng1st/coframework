package io.codeone.framework.api.aspect;

import io.codeone.framework.api.API;
import io.codeone.framework.api.factory.ApiPluginChainFactory;
import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.util.PluginChain;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Order(0)
@Component
public class ApiAspect {

    private final Logger logger = LoggerFactory.getLogger("coframework.api");

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
        Class<?> clazz = method.getDeclaringClass();
        if (method.isAnnotationPresent(EnablePlugin.class)
                || clazz.isAnnotationPresent(EnablePlugin.class)) {
            logger.warn("Found both @API and @EnablePlugin on '" + method
                    + "' or its class, which will cause that the stage of"
                    + " plugins work incorrectly");
        }
        PluginChain pluginChain;
        if (api.enablePlugins().length == 0) {
            pluginChain = apiPluginChainFactory.getDefaultChain();
        } else if (atClassLevel) {
            pluginChain = apiPluginChainFactory.getChainOfClass(
                    clazz, api.enablePlugins());
        } else {
            pluginChain = apiPluginChainFactory.getChainOfMethod(
                    method, api.enablePlugins());
        }
        return pluginChain.intercept(method, pjp.getArgs(),
                () -> pjp.proceed(pjp.getArgs()));
    }
}
