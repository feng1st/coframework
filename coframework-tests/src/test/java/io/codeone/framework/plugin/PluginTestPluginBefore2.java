package io.codeone.framework.plugin;

import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.List;

@Plug(value = Stages.BEFORE_TARGET, targetAnnotations = PluginTestAnno.class)
@Order(2)
@SuppressWarnings("unchecked")
public class PluginTestPluginBefore2 implements Plugin {

    @Override
    public void before(Method method, Object[] args) {
        Plugin.super.before(method, args);
        ((List<Object>) args[0]).add("before-2-before");
    }

    @Override
    public Object afterThrowing(Method method, Object[] args, Throwable throwable) throws Throwable {
        ((List<Object>) args[0]).add("before-2-after-throwing");
        return Plugin.super.afterThrowing(method, args, throwable);
    }

    @Override
    public Object afterReturning(Method method, Object[] args, Object result) throws Throwable {
        ((List<Object>) args[0]).add("before-2-after-returning");
        return Plugin.super.afterReturning(method, args, result);
    }
}
