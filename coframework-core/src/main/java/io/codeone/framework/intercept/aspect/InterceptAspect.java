package io.codeone.framework.intercept.aspect;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiInterceptorFactory;
import io.codeone.framework.intercept.Interceptor;
import io.codeone.framework.intercept.InterceptorChain;
import io.codeone.framework.intercept.InterceptorChainFactory;
import io.codeone.framework.intercept.util.MethodWrap;
import io.codeone.framework.intercept.util.MethodWrapFactory;
import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.PluginFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Order(0)
@Component
public class InterceptAspect {

    @Resource
    private ApiInterceptorFactory apiInterceptorFactory;

    @Resource
    private PluginFactory pluginFactory;

    @Resource
    private InterceptorChainFactory interceptorChainFactory;

    @Around("@within(io.codeone.framework.api.API)"
            + " || @annotation(io.codeone.framework.api.API)"
            + " || @within(io.codeone.framework.plugin.EnablePlugin)"
            + " || @annotation(io.codeone.framework.plugin.EnablePlugin)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        InterceptorChain interceptorChain = getChain(method);
        return interceptorChain.intercept(method, pjp.getArgs(), pjp::proceed);
    }

    public InterceptorChain getChain(Method method) {
        MethodWrap methodWrap = MethodWrapFactory.get(method);

        EnablePlugin methodEnablePlugin = methodWrap.getMethodAnnotation(
                EnablePlugin.class);
        EnablePlugin classEnablePlugin = methodWrap.getClassAnnotation(
                EnablePlugin.class);
        boolean api = methodWrap.isAnnotationPresent(API.class);

        if (methodEnablePlugin != null) {
            return interceptorChainFactory.getPluginChain(
                    methodWrap.getMethod(),
                    () -> getInterceptors(methodEnablePlugin, api));
        }
        if (classEnablePlugin != null) {
            return interceptorChainFactory.getPluginChain(
                    methodWrap.getDeclaringClass(),
                    () -> getInterceptors(classEnablePlugin, api));
        }
        if (api) {
            return interceptorChainFactory.getApiInterceptorChain();
        }
        return null;
    }

    private List<Interceptor<?>> getInterceptors(EnablePlugin enablePlugin,
                                                 boolean api) {
        List<Interceptor<?>> interceptors = new ArrayList<>(
                pluginFactory.getInterceptors(enablePlugin.value()));
        if (api) {
            interceptors.addAll(apiInterceptorFactory.getInterceptors());
        }
        return interceptors;
    }
}
