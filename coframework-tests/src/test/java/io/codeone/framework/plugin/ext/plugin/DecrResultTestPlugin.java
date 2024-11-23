package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;

import java.lang.reflect.Method;

@Plug(Stages.RESULT_INTERCEPTING)
public class DecrResultTestPlugin implements Plugin {

    @Override
    public Object afterReturning(Method method, Object[] args,
                                 Object result) throws Throwable {
        if (result instanceof Long) {
            return (Long) result - 5L;
        }
        if (result instanceof Integer) {
            return (Integer) result - 5;
        }
        if (result instanceof String) {
            return "";
        }
        return result;
    }
}
