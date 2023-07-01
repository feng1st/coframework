package io.codeone.framework.plugin.plug.impl;

import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.factory.PluginFactory;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class DefaultAnnotationMethodPlugger
        extends AnnotationMethodPlugger<EnablePlugin> {

    @Resource
    private PluginFactory pluginFactory;

    @Override
    protected Class<EnablePlugin> getAnnotationType() {
        return EnablePlugin.class;
    }

    @Override
    protected List<Plugin<?>> getPlugins(Method method,
                                         EnablePlugin annotation) {
        return pluginFactory.getPlugins(annotation.value());
    }
}
