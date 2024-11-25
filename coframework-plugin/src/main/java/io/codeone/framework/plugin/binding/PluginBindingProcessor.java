package io.codeone.framework.plugin.binding;

import java.lang.reflect.Method;

public interface PluginBindingProcessor {

    void processAfterBinding(Method method, Class<?> targetClass);
}
