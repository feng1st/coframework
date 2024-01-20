package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.TargetMethod;

@Plug(Stages.POST_RESULT_INTERCEPTING)
public class ExToZeroTestPlugin implements Plugin {

    @Override
    public Object afterThrowing(TargetMethod targetMethod, Object[] args,
                                Throwable error) throws Throwable {
        if (targetMethod.getReturnType() == Long.class) {
            return 0L;
        }
        if (targetMethod.getReturnType() == Integer.class) {
            return 0;
        }
        throw error;
    }
}
