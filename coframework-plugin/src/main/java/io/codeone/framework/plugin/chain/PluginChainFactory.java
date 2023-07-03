package io.codeone.framework.plugin.chain;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.MethodPluggers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class PluginChainFactory {

    @Resource
    private MethodPluggers methodPluggers;

    private final Map<Method, PluginChain> chainMap = new ConcurrentHashMap<>();

    private final Map<Set<Class<?>>, PluginChain> pluginClassMap = new ConcurrentHashMap<>();

    public PluginChain getChain(Method method) {
        return chainMap.computeIfAbsent(method, k1 -> {
            List<Plugin> plugins = methodPluggers.getPlugins(method);
            Set<Class<?>> pluginClasses = plugins.stream()
                    .map(Object::getClass)
                    .collect(Collectors.toSet());
            return pluginClassMap.computeIfAbsent(pluginClasses,
                    k2 -> new PluginChain(plugins));
        });
    }
}
