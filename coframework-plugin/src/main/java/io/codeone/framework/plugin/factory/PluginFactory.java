package io.codeone.framework.plugin.factory;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * The factory of plugins. In which plugins are stored and accessed by classes
 * and by groups.
 */
@Component
public class PluginFactory {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<Class<?>, Plugin> classMap = new HashMap<>();

    private final Map<String, List<Plugin>> groupMap = new HashMap<>();

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    @EventListener
    public void onApplicationContextInitialized(ContextRefreshedEvent event) {
        if (initialized.compareAndSet(false, true)) {
            applicationContext.getBeansOfType(Plugin.class).values()
                    .forEach(this::registerPlugin);
        }
    }

    private void registerPlugin(Plugin plugin) {
        Class<?> clazz = plugin.getClass();

        classMap.put(clazz, plugin);

        Plug plug = clazz.getAnnotation(Plug.class);
        if (plug != null
                && !plug.group().isEmpty()) {
            groupMap.computeIfAbsent(plug.group(), k -> new ArrayList<>())
                    .add(plugin);
        }
    }

    /**
     * Gets plugins by {@link Plugging}, which uses a variation of Visitor
     * Pattern to indicate how to get plugins from this factory, for example, by
     * classes or by group.
     *
     * @param plugging which defined how plugins should be acquired from this
     *                 factory
     * @return list of plugins associated with the {@code plugging}
     */
    public List<Plugin> getPlugins(Plugging plugging) {
        return plugging.getPlugins(this);
    }

    /**
     * Gets plugins by group, which declared in {@link Plug#group()}.
     *
     * @param group group of plugins, used for getting multiple plugins at once
     * @return list of plugins that belong to the specified group
     */
    public List<Plugin> getPlugins(String group) {
        return groupMap.get(group);
    }

    /**
     * Gets plugins by their classes.
     *
     * @param pluginClasses classes of plugins that are being acquired
     * @return list of plugins of the specified classes
     */
    @SafeVarargs
    public final List<Plugin> getPlugins(Class<? extends Plugin>... pluginClasses) {
        return Arrays.stream(pluginClasses)
                .map(classMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
