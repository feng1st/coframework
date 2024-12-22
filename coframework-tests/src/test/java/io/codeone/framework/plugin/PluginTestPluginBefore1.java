package io.codeone.framework.plugin;

import org.junit.jupiter.api.Order;

import java.lang.reflect.Method;
import java.util.List;

@Plug(value = Stages.BEFORE_TARGET, targetAnnotations = PluginTestAnno.class)
@Order(1)
@SuppressWarnings("unchecked")
public class PluginTestPluginBefore1 implements Plugin {

    @Override
    public void before(Method method, Object[] args) {
        Plugin.super.before(method, args);
        ((List<Object>) args[0]).add("before-1-before");
    }

    @Override
    public Object afterThrowing(Method method, Object[] args, Throwable throwable) throws Throwable {
        ((List<Object>) args[0]).add("before-1-after-throwing");
        return Plugin.super.afterThrowing(method, args, throwable);
    }

    @Override
    public Object afterReturning(Method method, Object[] args, Object result) throws Throwable {
        ((List<Object>) args[0]).add("before-1-after-returning");
        return Plugin.super.afterReturning(method, args, result);
    }
}
