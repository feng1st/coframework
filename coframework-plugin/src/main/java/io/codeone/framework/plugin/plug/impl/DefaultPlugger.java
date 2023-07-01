package io.codeone.framework.plugin.plug.impl;

import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;
import io.codeone.framework.plugin.plug.Plugger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class DefaultPlugger implements Plugger {

    @Resource
    private PluginFactory pluginFactory;

    @Override
    public boolean isPlugged(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        return method.isAnnotationPresent(EnablePlugin.class)
                || clazz.isAnnotationPresent(EnablePlugin.class);
    }

    @Override
    public List<Plugin<?>> getPlugins(Method method) {
        EnablePlugin enablePlugin;
        if ((enablePlugin = method
                .getAnnotation(EnablePlugin.class)) != null) {
            return pluginFactory.getPlugins(enablePlugin.value());
        }
        if ((enablePlugin = method.getDeclaringClass()
                .getAnnotation(EnablePlugin.class)) != null) {
            return pluginFactory.getPlugins(enablePlugin.value());
        }
        return null;
    }
}
