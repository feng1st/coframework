package io.codeone.framework.plugin.binding;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.List;

@Plug(targetAnnotations = PluginBindingProcessorTestAnno.class)
public class PluginBindingProcessorTestPlugin implements Plugin, PluginBindingProcessor {

    public static boolean processed = false;

    @Override
    public void processAfterBinding(Method method, Class<?> targetClass) {
        processed = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void before(Method method, Object[] args) {
        if (processed) {
            ((List<Object>) args[0]).add("processed");
        }
    }
}
