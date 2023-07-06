package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.chain.PluginChainContext;
import io.codeone.framework.plugin.util.TargetMethod;

@Plug(Stages.BEFORE_TARGET)
public class ContextWriterTestPlugin implements Plugin {

    @Override
    public void before(TargetMethod targetMethod, Object[] args) {
        PluginChainContext.put("test", 1);
    }
}
