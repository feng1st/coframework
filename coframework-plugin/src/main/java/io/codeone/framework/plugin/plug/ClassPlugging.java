package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plugin.PluginFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * A {@code Plugging} that gets plugins from {@link PluginFactory} by their
 * classes.
 */
@RequiredArgsConstructor
public class ClassPlugging implements Plugging {

    private final Class<? extends Plugin>[] classes;

    /**
     * Constructs a {@code ClassPlugging} by specifying the classes of its
     * associated plugins.
     *
     * @param classes classes of its associated plugins
     * @return constructed {@code ClassPlugging}
     */
    @SafeVarargs
    public static ClassPlugging of(Class<? extends Plugin>... classes) {
        return new ClassPlugging(classes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Plugin> getPlugins(PluginFactory pluginFactory) {
        return pluginFactory.getPlugins(classes);
    }
}
