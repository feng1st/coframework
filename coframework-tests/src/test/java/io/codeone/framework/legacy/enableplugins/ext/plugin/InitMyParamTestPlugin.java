package io.codeone.framework.legacy.enableplugins.ext.plugin;

import io.codeone.framework.legacy.enableplugins.domain.param.MyBizParam;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;

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
