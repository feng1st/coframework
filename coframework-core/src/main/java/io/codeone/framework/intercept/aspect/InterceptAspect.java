package io.codeone.framework.intercept.aspect;

import io.codeone.framework.api.API;
import io.codeone.framework.intercept.InterceptorChain;
import io.codeone.framework.intercept.InterceptorChainFactory;
import io.codeone.framework.intercept.util.MethodWrap;
import io.codeone.framework.intercept.util.MethodWrapFactory;
import io.codeone.framework.plugin.EnablePlugin;
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
public class InterceptAspect {

    @Resource
    private InterceptorChainFactory interceptorChainFactory;

    @Around("@within(io.codeone.framework.api.API)"
            + " || @annotation(io.codeone.framework.api.API)"
            + " || @within(io.codeone.framework.plugin.EnablePlugin)"
            + " || @annotation(io.codeone.framework.plugin.EnablePlugin)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        InterceptorChain interceptorChain = getChain(method);
        if (interceptorChain == null) {
            return pjp.proceed();
        }
        return interceptorChain.intercept(
                method, pjp.getArgs(), pjp::proceed);
    }

    public InterceptorChain getChain(Method method) {
        MethodWrap methodWrap = MethodWrapFactory.get(method);

        boolean isApi = methodWrap.isAnnotationPresent(API.class);
        EnablePlugin enablePlugin;
        if ((enablePlugin = methodWrap.getMethodAnnotation(EnablePlugin.class))
                != null) {
            return interceptorChainFactory.getPluginChainOfMethod(
                    methodWrap.getMethod(),
                    enablePlugin.value(), isApi);
        }
        if ((enablePlugin = methodWrap.getClassAnnotation(EnablePlugin.class))
                != null) {
            return interceptorChainFactory.getPluginChainOfClass(
                    methodWrap.getDeclaringClass(),
                    enablePlugin.value(), isApi);
        }
        if (isApi) {
            return interceptorChainFactory.getApiInterceptorChain();
        }
        return null;
    }
}
