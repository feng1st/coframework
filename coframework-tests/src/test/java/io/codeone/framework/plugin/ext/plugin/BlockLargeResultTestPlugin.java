package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.MethodWrap;
import org.springframework.stereotype.Component;

@Component
@Plug(Stages.RESULT_VALIDATING)
public class BlockLargeResultTestPlugin implements Plugin<Void> {

    @Override
    public Object afterReturning(MethodWrap methodWrap, Object[] args,
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
