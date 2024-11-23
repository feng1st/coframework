package io.codeone.framework.plugin.binding;

import io.codeone.framework.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.Set;

public interface MethodPluginBindingRepo {

    boolean dynamicBind(Method method);

    Set<Class<? extends Plugin>> getPluginClasses(Method method);
}
