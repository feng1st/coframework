package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ClassPlugging implements Plugging {

    private final Class<? extends Plugin>[] classes;

    @SafeVarargs
    public static ClassPlugging of(Class<? extends Plugin>... classes) {
        return new ClassPlugging(classes);
    }

    @Override
    public List<Plugin> getPlugins(PluginFactory pluginFactory) {
        return pluginFactory.getPlugins(classes);
    }
}
