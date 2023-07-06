package io.codeone.framework.ext.scope;

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
public class BizScenarioScopePlugin implements Plugin {

    @Resource
    private BizScenarioScopeIndexer bizScenarioScopeIndexer;

    @Override
    public Object around(TargetMethod targetMethod, Object[] args, Invokable<?> invokable)
            throws Throwable {
        Method method = targetMethod.getMethod();
        BizScenarioScope scope = targetMethod.getAnnotation(BizScenarioScope.class);
        BizScenario bizScenario = bizScenarioScopeIndexer.resolve(method, args, scope);
        if (bizScenario == null) {
            throw new IllegalArgumentException("Could not resolve BizScenario from args of '" + method + "'");
        }
        return BizScenarioContext.invoke(bizScenario, invokable);
    }
}
