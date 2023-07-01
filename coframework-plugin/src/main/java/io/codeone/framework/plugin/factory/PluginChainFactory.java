package io.codeone.framework.plugin.factory;

import io.codeone.framework.plugin.plug.Pluggers;
import io.codeone.framework.plugin.util.PluginChain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PluginChainFactory {

    @Resource
    private Pluggers pluggers;

    private final Map<Method, PluginChain> methodChainMap
            = new ConcurrentHashMap<>();

    public PluginChain getChain(Method method) {
        return methodChainMap.computeIfAbsent(method,
                k -> new PluginChain(pluggers.getPlugins(method)));
    }
}
