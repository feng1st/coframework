package io.codeone.framework.plugin.binding;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;

import java.lang.reflect.Method;

@Plug()
public class PluginBindingProcessorTestPlugin implements Plugin, PluginBindingProcessor {

    @Override
    public void processAfterBinding(Method method, Class<?> targetClass) {

    }

    @Override
    public void before(Method method, Object[] args) {
        Plugin.super.before(method, args);
    }
}
