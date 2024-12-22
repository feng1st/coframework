package io.codeone.framework.plugin.binding;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.List;

@Plug
@Order(1)
public class AnnoPluginBindingTestPluginFoo implements Plugin {

    @Override
    @SuppressWarnings("unchecked")
    public void before(Method method, Object[] args) {
        ((List<Object>) args[0]).add("foo");
    }
}
