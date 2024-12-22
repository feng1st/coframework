package io.codeone.framework.plugin;

import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.List;

@Plug(value = Stages.AFTER_TARGET, targetAnnotations = PluginTestAnno.class)
@Order(1)
@SuppressWarnings("unchecked")
public class PluginTestPluginAfter1 implements Plugin {

    @Override
    public void before(Method method, Object[] args) {
        Plugin.super.before(method, args);
        ((List<Object>) args[0]).add("after-1-before");
    }

    @Override
    public Object afterThrowing(Method method, Object[] args, Throwable throwable) throws Throwable {
        ((List<Object>) args[0]).add("after-1-after-throwing");
        return Plugin.super.afterThrowing(method, args, throwable);
    }

    @Override
    public Object afterReturning(Method method, Object[] args, Object result) throws Throwable {
        ((List<Object>) args[0]).add("after-1-after-returning");
        return Plugin.super.afterReturning(method, args, result);
    }
}
