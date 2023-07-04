package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Decides how to plug plugins.
 */
public interface Plugging {

    static List<Plugging> asList(Plugging... pluggingList) {
        return Arrays.asList(pluggingList);
    }

    /**
     * Uses visitor pattern to get plugins that will be plugged.
     */
    List<Plugin> getPlugins(PluginFactory pluginFactory);
}
