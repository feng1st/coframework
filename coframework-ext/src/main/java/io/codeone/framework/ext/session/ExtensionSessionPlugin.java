package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.TargetMethod;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Plug(Stages.BEFORE_TARGET)
public class ExtensionSessionPlugin implements Plugin {

    @Resource
    private ExtensionSessionIndexer extensionSessionIndexer;

    @Override
    public Object around(TargetMethod targetMethod, Object[] args, Invokable<?> invokable)
            throws Throwable {
        Method method = targetMethod.getMethod();
        ExtensionSession session = targetMethod.getAnnotation(ExtensionSession.class);
        BizScenario bizScenario = extensionSessionIndexer.resolve(method, args, session);
        if (bizScenario == null) {
            throw new IllegalArgumentException("Could not resolve BizScenario from args of '" + method + "'");
        }
        return BizScenarioContext.invoke(bizScenario, invokable);
    }
}
