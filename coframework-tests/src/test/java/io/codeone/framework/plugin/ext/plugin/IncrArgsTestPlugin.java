package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Stage;
import io.codeone.framework.intercept.util.TargetMethod;
import io.codeone.framework.plugin.Plugin;
import org.springframework.stereotype.Component;

@Component
@Intercept(Stage.ARG_PREPARING)
public class IncrArgsTestPlugin implements Plugin<Void> {

    @Override
    public void before(TargetMethod method, Object[] args) throws Throwable {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Long) {
                args[i] = ((Long) args[i]) + 1L;
            } else if (args[i] instanceof Integer) {
                args[i] = ((Integer) args[i]) + 1;
            }
        }
    }
}
