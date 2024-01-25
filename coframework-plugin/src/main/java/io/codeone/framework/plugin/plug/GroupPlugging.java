package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * A {@code Plugging} that gets plugins from the {@link PluginFactory} by their
 * group.
 */
@RequiredArgsConstructor(staticName = "of")
public class GroupPlugging implements Plugging {

    private final String group;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Plugin> getPlugins(PluginFactory pluginFactory) {
        return pluginFactory.getPlugins(group);
    }
}
