package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Stage;
import io.codeone.framework.intercept.util.Signature;
import io.codeone.framework.plugin.Plugin;
import org.springframework.stereotype.Component;

@Component
@Intercept(Stage.RESULT_VALIDATING)
public class BlockSmallResultTestPlugin implements Plugin<Void> {

    @Override
    public Object afterReturning(Signature signature, Object[] args,
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
