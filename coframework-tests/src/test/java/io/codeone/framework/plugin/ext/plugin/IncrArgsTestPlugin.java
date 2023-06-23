package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stage;
import io.codeone.framework.plugin.util.MethodWrap;
import org.springframework.stereotype.Component;

@Component
@Plug(Stage.ARG_PREPARING)
public class IncrArgsTestPlugin implements Plugin<Void> {

    @Override
    public void before(MethodWrap methodWrap, Object[] args)
            throws Throwable {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Long) {
                args[i] = ((Long) args[i]) + 1L;
            } else if (args[i] instanceof Integer) {
                args[i] = ((Integer) args[i]) + 1;
            }
        }
    }
}
