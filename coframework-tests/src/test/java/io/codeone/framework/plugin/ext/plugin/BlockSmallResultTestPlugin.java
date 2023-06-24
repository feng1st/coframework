package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.MethodWrap;
import org.springframework.stereotype.Component;

@Component
@Plug(Stages.RESULT_VALIDATING)
public class BlockSmallResultTestPlugin implements Plugin<Void> {

    @Override
    public Object afterReturning(MethodWrap methodWrap, Object[] args,
                                 Object result) throws Throwable {
        if ((result instanceof Long
                && (Long) result < 10L)
                || (result instanceof Integer
                && (Integer) result < 10)) {
            throw new IllegalStateException("Result is too small");
        }
        return result;
    }
}
