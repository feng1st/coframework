package io.codeone.framework.plugin.aspect;

import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.factory.PluginChainFactory;
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
@Order(1)
@Component
public class PluginAspect {

    @Resource
    private PluginChainFactory pluginChainFactory;

    @Around("@within(enablePlugin)"
            + " && !@annotation(io.codeone.framework.plugin.EnablePlugin)")
    public Object aroundClass(ProceedingJoinPoint pjp,
                              EnablePlugin enablePlugin) throws Throwable {
        return around(pjp, enablePlugin, true);
    }

    @Around("@annotation(enablePlugin)")
    public Object aroundMethod(ProceedingJoinPoint pjp,
                               EnablePlugin enablePlugin) throws Throwable {
        return around(pjp, enablePlugin, false);
    }

    private Object around(ProceedingJoinPoint pjp, EnablePlugin enablePlugin,
                          boolean atClassLevel) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        PluginChain pluginChain;
        if (atClassLevel) {
            pluginChain = pluginChainFactory.getChainOfClass(
                    method.getDeclaringClass(), enablePlugin.value());
        } else {
            pluginChain = pluginChainFactory.getChainOfMethod(
                    method, enablePlugin.value());
        }
        return pluginChain.intercept(method, pjp.getArgs(), pjp::proceed);
    }
}
