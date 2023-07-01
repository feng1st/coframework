package io.codeone.framework.api.plug.impl;

import io.codeone.framework.api.API;
import io.codeone.framework.api.factory.ApiPluginFactory;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.Plugger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class ApiPlugger implements Plugger {

    @Resource
    private ApiPluginFactory apiPluginFactory;

    @Override
    public boolean isPlugged(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        return method.isAnnotationPresent(API.class)
                || clazz.isAnnotationPresent(API.class);
    }

    @Override
    public List<Plugin<?>> getPlugins(Method method) {
        return apiPluginFactory.getPlugins();
    }
}
