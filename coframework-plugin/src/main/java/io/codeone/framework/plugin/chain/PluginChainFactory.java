package io.codeone.framework.plugin.chain;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;
import io.codeone.framework.plugin.plug.MethodPluggers;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class PluginChainFactory {

    @Resource
    private MethodPluggers methodPluggers;

    @Resource
    private PluginFactory pluginFactory;

    private final Map<Method, PluginChain> chainMap = new ConcurrentHashMap<>();

    private final Map<Set<Class<?>>, PluginChain> pluginClassMap = new ConcurrentHashMap<>();

    public PluginChain getChain(Method method) {
        return chainMap.computeIfAbsent(method, k1 -> {
            List<Plugging> pluggingList = methodPluggers.getPluggingList(method);
            List<Plugin> plugins = pluggingList.stream()
                    .map(pluginFactory::getPlugins)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .distinct()
                    .collect(Collectors.toList());
            Set<Class<?>> pluginClasses = plugins.stream()
                    .map(Object::getClass)
                    .collect(Collectors.toSet());
            return pluginClassMap.computeIfAbsent(pluginClasses,
                    k2 -> new PluginChain(plugins));
        });
    }
}
