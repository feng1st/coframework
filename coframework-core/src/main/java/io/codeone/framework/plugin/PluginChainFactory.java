package io.codeone.framework.plugin;

import io.codeone.framework.api.ApiPluginFactory;
import io.codeone.framework.plugin.chain.PluginChain;
import io.codeone.framework.plugin.factory.PluginFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// FIXME
@Component
public class PluginChainFactory {

    @Resource
    private ApiPluginFactory apiPluginFactory;

    @Resource
    private PluginFactory pluginFactory;

    private PluginChain apiPluginChain;

    private final Map<Method, PluginChain> methodPluginMap
            = new ConcurrentHashMap<>();

    private final Map<Class<?>, PluginChain> classPluginMap
            = new ConcurrentHashMap<>();

    public PluginChain getApiPluginChain() {
        PluginChain chain;
        if ((chain = apiPluginChain) == null) {
            synchronized (this) {
                if ((chain = apiPluginChain) == null) {
                    chain = new PluginChain(
                            apiPluginFactory.getApiPlugins());
                    apiPluginChain = chain;
                }
            }
        }
        return chain;
    }

    public PluginChain getPluginChainOfMethod(
            Method method, Class<? extends Plugin<?>>[] pluginClasses,
            boolean isApi) {
        return methodPluginMap.computeIfAbsent(method,
                k -> getPluginChain(pluginClasses, isApi));
    }

    public PluginChain getPluginChainOfClass(
            Class<?> clazz, Class<? extends Plugin<?>>[] pluginClasses,
            boolean isApi) {
        return classPluginMap.computeIfAbsent(clazz,
                k -> getPluginChain(pluginClasses, isApi));
    }

    private PluginChain getPluginChain(
            Class<? extends Plugin<?>>[] pluginClasses, boolean isApi) {
        List<Plugin<?>> plugins = new ArrayList<>();
        plugins.addAll(pluginFactory.getPlugins(pluginClasses));
        if (isApi) {
            plugins.addAll(apiPluginFactory.getApiPlugins());
        }
        return new PluginChain(plugins);
    }
}
