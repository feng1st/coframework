package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.List;

public interface Plugger {

    boolean isPlugged(Method method);

    List<Plugin<?>> getPlugins(Method method);
}
