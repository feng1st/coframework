package io.codeone.framework.plugin.enableplugins.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.enableplugins.domain.param.MyBizParam;

import java.lang.reflect.Method;

@Plug(Stages.PRE_ARG_INTERCEPTING)
public class InitMyParamTestPlugin implements Plugin {

    @Override
    public void before(Method method, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof MyBizParam
                    && ((MyBizParam) args[i]).getId() == null) {
                ((MyBizParam) args[i]).setId(0L);
            }
        }
    }
}
