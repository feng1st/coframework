package io.codeone.framework.plugin.factory;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.MethodPluggers;
import io.codeone.framework.plugin.plug.Plugging;
import io.codeone.framework.plugin.pluginchain.PluginChain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * The factory of the plugin chain.
 */
@Component
public class PluginChainFactory {

    @Resource
    private MethodPluggers methodPluggers;

    @Resource
    private PluginFactory pluginFactory;

    private final Map<Method, PluginChain> chainMap = new ConcurrentHashMap<>();

    private final Map<Set<Class<?>>, PluginChain> pluginClassMap = new ConcurrentHashMap<>();

    /**
     * Returns a cached plugin chain for the specified method, creates one if
     * not exist. What plugins should be used for the method is determined by
     * {@link MethodPluggers}.
     *
     * @param method the target method to be intercepted
     * @return the plugin chain for the specified method
     */
    public PluginChain getChain(Method method) {
        return chainMap.computeIfAbsent(method, k -> {
            List<Plugging> pluggingList = methodPluggers.getPluggingList(method);
            return getChain(pluggingList);
        });
    }

    private PluginChain getChain(List<Plugging> pluggingList) {
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
                k -> new PluginChain(plugins));
    }
}
