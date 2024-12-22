package io.codeone.framework.plugin;

import java.lang.reflect.Method;
import java.util.List;

@Plug(value = Stages.ARG_INTERCEPTING, targetAnnotations = PluginTestAnno.class)
@SuppressWarnings("unchecked")
public class PluginTestPluginArgIntercepting implements Plugin {

    @Override
    public void before(Method method, Object[] args) {
        Plugin.super.before(method, args);
        ((List<Object>) args[0]).add("arg-before");
    }

    @Override
    public Object afterThrowing(Method method, Object[] args, Throwable throwable) throws Throwable {
        ((List<Object>) args[0]).add("arg-after-throwing");
        return Plugin.super.afterThrowing(method, args, throwable);
    }

    @Override
    public Object afterReturning(Method method, Object[] args, Object result) throws Throwable {
        ((List<Object>) args[0]).add("arg-after-returning");
        return Plugin.super.afterReturning(method, args, result);
    }
}