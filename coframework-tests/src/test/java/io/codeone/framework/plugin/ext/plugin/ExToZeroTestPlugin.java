package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;

import java.lang.reflect.Method;

@Plug(Stages.POST_RESULT_INTERCEPTING)
public class ExToZeroTestPlugin implements Plugin {

    @Override
    public Object afterThrowing(Method method, Object[] args,
                                Throwable throwable) throws Throwable {
        if (method.getReturnType() == Long.class) {
            return 0L;
        }
        if (method.getReturnType() == Integer.class) {
            return 0;
        }
        throw throwable;
    }
}
