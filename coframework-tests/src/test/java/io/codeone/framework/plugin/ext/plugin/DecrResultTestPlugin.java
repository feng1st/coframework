package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.TargetMethod;

@Plug(Stages.RESULT_INTERCEPTING)
public class DecrResultTestPlugin implements Plugin {

    @Override
    public Object afterReturning(TargetMethod targetMethod, Object[] args,
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
