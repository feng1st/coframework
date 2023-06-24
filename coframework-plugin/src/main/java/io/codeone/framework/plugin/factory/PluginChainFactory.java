package io.codeone.framework.plugin.factory;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.util.PluginChain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PluginChainFactory {

    @Resource
    private PluginFactory pluginFactory;

    private final Map<Method, PluginChain> methodChainMap
            = new ConcurrentHashMap<>();

    private final Map<Class<?>, PluginChain> classChainMap
            = new ConcurrentHashMap<>();

    public PluginChain getChainOfClass(
            Class<?> clazz, Class<? extends Plugin<?>>[] pluginClasses) {
        return classChainMap.computeIfAbsent(clazz,
                k -> new PluginChain(pluginFactory.getPlugins(pluginClasses)));
    }

    public PluginChain getChainOfMethod(
            Method method, Class<? extends Plugin<?>>[] pluginClasses) {
        return methodChainMap.computeIfAbsent(method,
                k -> new PluginChain(pluginFactory.getPlugins(pluginClasses)));
    }
}
