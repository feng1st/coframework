package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Stage;
import io.codeone.framework.intercept.util.TargetMethod;
import io.codeone.framework.plugin.Plugin;
import org.springframework.stereotype.Component;

@Component
@Intercept(Stage.EXCEPTION_HANDLING)
public class ExToZeroTestPlugin implements Plugin<Void> {

    @Override
    public Object afterThrowing(TargetMethod method, Object[] args,
                                Throwable error) throws Throwable {
        if (method.getReturnType() == Long.class) {
            return 0L;
        }
        if (method.getReturnType() == Integer.class) {
            return 0;
        }
        throw error;
    }
}
