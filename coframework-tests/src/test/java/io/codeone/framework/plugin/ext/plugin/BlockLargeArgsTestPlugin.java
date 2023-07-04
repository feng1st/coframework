package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.MethodWrap;

@Plug(Stages.ARG_VALIDATION)
public class BlockLargeArgsTestPlugin implements Plugin {

    @Override
    public void before(MethodWrap methodWrap, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if ((args[i] instanceof Long
                    && (Long) args[i] > 10L)
                    || (args[i] instanceof Integer
                    && (Integer) args[i] > 10)) {
                throw new IllegalArgumentException(
                        "Arg" + i + " is too large");
            }
        }
    }
}
