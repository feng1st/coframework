package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.MethodWrap;
import org.springframework.stereotype.Component;

@Component
@Plug(Stages.RESULT_ADJUSTING)
public class DecrResultTestPlugin implements Plugin<Void> {

    @Override
    public Object afterReturning(MethodWrap methodWrap, Object[] args,
                                 Object result) throws Throwable {
        if (result instanceof Long) {
            return (Long) result - 5L;
        }
        if (result instanceof Integer) {
            return (Integer) result - 5;
        }
        return result;
    }
}
