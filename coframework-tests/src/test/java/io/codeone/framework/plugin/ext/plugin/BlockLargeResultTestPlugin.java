package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.TargetMethod;

@Plug(Stages.PRE_RESULT_INTERCEPTING)
public class BlockLargeResultTestPlugin implements Plugin {

    @Override
    public Object afterReturning(TargetMethod targetMethod, Object[] args,
                                 Object result) throws Throwable {
        if ((result instanceof Long
                && (Long) result > 21L)
                || (result instanceof Integer
                && (Integer) result > 21)) {
            throw new IllegalStateException("Result is too large");
        }
        return result;
    }
}
