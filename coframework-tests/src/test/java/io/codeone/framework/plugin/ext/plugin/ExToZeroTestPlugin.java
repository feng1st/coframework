package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.MethodWrap;

@Plug(Stages.EXCEPTION_HANDLING)
public class ExToZeroTestPlugin implements Plugin<Void> {

    @Override
    public Object afterThrowing(MethodWrap methodWrap, Object[] args,
                                Throwable error) throws Throwable {
        if (methodWrap.getReturnType() == Long.class) {
            return 0L;
        }
        if (methodWrap.getReturnType() == Integer.class) {
            return 0;
        }
        throw error;
    }
}
