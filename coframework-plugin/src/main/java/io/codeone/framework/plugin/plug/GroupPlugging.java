package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class GroupPlugging implements Plugging {

    private final String group;

    @Override
    public List<Plugin> getPlugins(PluginFactory pluginFactory) {
        return pluginFactory.getPlugins(group);
    }
}
