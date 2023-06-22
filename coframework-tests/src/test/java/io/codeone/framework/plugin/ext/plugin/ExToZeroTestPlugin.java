package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Stage;
import io.codeone.framework.intercept.util.Signature;
import io.codeone.framework.plugin.Plugin;
import org.springframework.stereotype.Component;

@Component
@Intercept(Stage.EXCEPTION_HANDLING)
public class ExToZeroTestPlugin implements Plugin<Void> {

    @Override
    public Object afterThrowing(Signature signature, Object[] args,
                                Throwable error) throws Throwable {
        if (signature.getReturnType() == Long.class) {
            return 0L;
        }
        if (signature.getReturnType() == Integer.class) {
            return 0;
        }
        throw error;
    }
}
