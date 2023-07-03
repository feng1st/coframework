package io.codeone.framework.api.enableplugins.ext.plugin;

import io.codeone.framework.api.enableplugins.domain.param.MyParam;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.MethodWrap;

@Plug(Stages.ARG_PREPARING)
public class InitMyParamTestPlugin implements Plugin {

    @Override
    public void before(MethodWrap methodWrap, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof MyParam
                    && ((MyParam) args[i]).getId() == null) {
                ((MyParam) args[i]).setId(0L);
            }
        }
    }
}
