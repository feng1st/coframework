package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;

import java.util.List;

public class GroupPlugging implements Plugging {

    private final String group;

    public static GroupPlugging of(String group) {
        return new GroupPlugging(group);
    }

    public GroupPlugging(String group) {
        this.group = group;
    }

    @Override
    public List<Plugin> getPlugins(PluginFactory pluginFactory) {
        return pluginFactory.getPlugins(group);
    }
}
