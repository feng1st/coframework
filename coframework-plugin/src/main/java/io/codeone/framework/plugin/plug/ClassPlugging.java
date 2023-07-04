package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;

import java.util.List;

public class ClassPlugging implements Plugging {

    private final Class<? extends Plugin>[] classes;

    @SafeVarargs
    public static ClassPlugging of(Class<? extends Plugin>... classes) {
        return new ClassPlugging(classes);
    }

    public ClassPlugging(Class<? extends Plugin>[] classes) {
        this.classes = classes;
    }

    @Override
    public List<Plugin> getPlugins(PluginFactory pluginFactory) {
        return pluginFactory.getPlugins(classes);
    }
}
