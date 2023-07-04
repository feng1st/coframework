package io.codeone.framework.plugin.ext.plugin;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.chain.PluginChainContext;
import io.codeone.framework.plugin.util.MethodWrap;

import java.util.Objects;

@Plug(Stages.AFTER_TARGET)
public class ContextReaderTestPlugin implements Plugin {

    @Override
    public void before(MethodWrap methodWrap, Object[] args) {
        PluginChainContext.put("test", 1);
    }

    @Override
    public Object after(MethodWrap methodWrap, Object[] args, Object result, Throwable error) throws Throwable {
        if (Objects.equals(PluginChainContext.get("test"), 1)) {
            throw new RuntimeException("Context is working");
        } else {
            throw new IllegalStateException("Context is not working");
        }
    }
}
