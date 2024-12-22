package io.codeone.framework.plugin.binding;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.List;

@Plug(Stages.AFTER_TARGET)
@Order(1)
public class EnablePluginTestPluginBar implements Plugin {

    @Override
    @SuppressWarnings("unchecked")
    public Object afterReturning(Method method, Object[] args, Object result) throws Throwable {
        ((List<Object>) result).add("bar");
        return result;
    }
}
