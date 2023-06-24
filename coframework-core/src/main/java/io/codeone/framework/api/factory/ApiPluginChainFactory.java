package io.codeone.framework.api.factory;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;
import io.codeone.framework.plugin.util.PluginChain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApiPluginChainFactory {

    @Resource
    private PluginFactory pluginFactory;

    @Resource
    private ApiPluginFactory apiPluginFactory;

    private PluginChain defaultChain;

    private final Map<Class<?>, PluginChain> classChainMap
            = new ConcurrentHashMap<>();

    private final Map<Method, PluginChain> methodChainMap
            = new ConcurrentHashMap<>();

    public PluginChain getDefaultChain() {
        PluginChain chain;
        if ((chain = defaultChain) == null) {
            synchronized (this) {
                if ((chain = defaultChain) == null) {
                    chain = new PluginChain(apiPluginFactory.getPlugins());
                    defaultChain = chain;
                }
            }
        }
        return chain;
    }

    public PluginChain getChainOfClass(
            Class<?> clazz, Class<? extends Plugin<?>>[] pluginClasses) {
        return classChainMap.computeIfAbsent(clazz,
                k -> getChain(pluginClasses));
    }

    public PluginChain getChainOfMethod(
            Method method, Class<? extends Plugin<?>>[] pluginClasses) {
        return methodChainMap.computeIfAbsent(method,
                k -> getChain(pluginClasses));
    }

    private PluginChain getChain(
            Class<? extends Plugin<?>>[] pluginClasses) {
        List<Plugin<?>> plugins = new ArrayList<>();
        plugins.addAll(apiPluginFactory.getPlugins());
        plugins.addAll(pluginFactory.getPlugins(pluginClasses));
        return new PluginChain(plugins);
    }
}
