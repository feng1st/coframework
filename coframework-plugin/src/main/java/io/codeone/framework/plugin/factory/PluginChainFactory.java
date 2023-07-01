package io.codeone.framework.plugin.factory;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.Pluggers;
import io.codeone.framework.plugin.util.PluginChain;
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
    private Pluggers pluggers;

    private final Map<Method, PluginChain> chainMap
            = new ConcurrentHashMap<>();

    private final Map<Set<Class<?>>, PluginChain> pluginTypeMap
            = new ConcurrentHashMap<>();

    public PluginChain getChain(Method method) {
        return chainMap.computeIfAbsent(method,
                k1 -> {
                    List<Plugin<?>> plugins = pluggers.getPlugins(method);
                    Set<Class<?>> pluginTypes = plugins.stream()
                            .map(Object::getClass)
                            .collect(Collectors.toSet());
                    return pluginTypeMap.computeIfAbsent(pluginTypes,
                            k2 -> new PluginChain(plugins));
                });
    }
}
