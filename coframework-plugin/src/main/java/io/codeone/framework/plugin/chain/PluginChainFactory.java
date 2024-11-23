package io.codeone.framework.plugin.chain;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.binding.MethodPluginBindingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class PluginChainFactory {

    @Autowired
    private MethodPluginBindingRepo methodPluginBindingRepo;

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<Method, PluginChain> chainMap = new ConcurrentHashMap<>();

    private final Map<Set<Class<? extends Plugin>>, PluginChain> pluginClassMap = new ConcurrentHashMap<>();

    public PluginChain getChain(Method method) {
        Set<Class<? extends Plugin>> pluginClasses = methodPluginBindingRepo.getPluginClasses(method);
        if (CollectionUtils.isEmpty(pluginClasses)) {
            return null;
        }
        return chainMap.computeIfAbsent(method, k -> getChain(pluginClasses));
    }

    private PluginChain getChain(Set<Class<? extends Plugin>> pluginClasses) {
        return pluginClassMap.computeIfAbsent(pluginClasses,
                k -> new PluginChain(pluginClasses.stream()
                        .map(pluginClass -> (Plugin) applicationContext.getBean(pluginClass))
                        .collect(Collectors.toList())));
    }
}
