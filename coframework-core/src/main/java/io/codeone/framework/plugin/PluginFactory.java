package io.codeone.framework.plugin;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PluginFactory {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<Class<?>, Plugin<?>> plugins = new HashMap<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(Plugin.class).values()
                .forEach(o -> plugins.put(o.getClass(), o));
    }

    public List<Plugin<?>> getPlugins(
            Class<? extends Plugin<?>>[] pluginClasses) {
        Objects.requireNonNull(pluginClasses);
        return Arrays.stream(pluginClasses)
                .map(plugins::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
