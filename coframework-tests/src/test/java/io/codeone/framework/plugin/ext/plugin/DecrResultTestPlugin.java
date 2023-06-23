package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Stage;
import io.codeone.framework.intercept.util.MethodWrap;
import io.codeone.framework.plugin.Plugin;
import org.springframework.stereotype.Component;

@Component
@Intercept(Stage.RESULT_ADJUSTING)
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
