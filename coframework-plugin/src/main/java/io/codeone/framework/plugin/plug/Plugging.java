package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;

import java.util.List;

/**
 * Decides how to acquire plugins from the {@link PluginFactory}.
 */
public interface Plugging {

    /**
     * Gets plugins from the {@link PluginFactory}.
     *
     * @param pluginFactory where plugins are stored and acquired from
     * @return list of plugins this {@code Plugging} associated with
     */
    List<Plugin> getPlugins(PluginFactory pluginFactory);
}
