package io.codeone.framework.plugin.factory;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.Plugging;
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

    private final Map<Class<?>, Plugin> pluginMap = new HashMap<>();

    private final Map<String, List<Plugin>> groupMap = new HashMap<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(Plugin.class).values()
                .forEach(this::addPlugin);
    }

    private void addPlugin(Plugin plugin) {
        Class<?> clazz = plugin.getClass();
        pluginMap.put(clazz, plugin);

        Plug plug = clazz.getAnnotation(Plug.class);
        if (plug != null
                && !plug.group().isEmpty()) {
            groupMap.computeIfAbsent(plug.group(), k -> new ArrayList<>())
                    .add(plugin);
        }
    }

    public List<Plugin> getPlugins(String group) {
        return groupMap.get(group);
    }

    public List<Plugin> getPlugins(Class<? extends Plugin>[] pluginClasses) {
        return Arrays.stream(pluginClasses)
                .map(pluginMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Plugin> getPlugins(Plugging plugging) {
        return plugging.getPlugins(this);
    }
}
