package io.codeone.framework.ext.scope;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.ext.repo.BizScenarioScopeRepo;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.MethodWrap;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Plug(Stages.BEFORE_TARGET)
public class BizScenarioScopePlugin implements Plugin {

    @Resource
    private BizScenarioScopeRepo bizScenarioScopeRepo;

    @Override
    public Object around(MethodWrap methodWrap, Object[] args, Invokable<?> invokable) throws Throwable {
        Method method = methodWrap.getMethod();
        BizScenarioScope scope = methodWrap.getAnnotation(BizScenarioScope.class);
        BizScenario bizScenario = resolveBizScenario(method, args, scope);
        if (bizScenario == null) {
            throw new IllegalArgumentException("Could not resolve BizScenario from args of '" + method + "'");
        }
        return BizScenarioContext.invoke(bizScenario, invokable::invoke);
    }

    private BizScenario resolveBizScenario(Method method, Object[] args, BizScenarioScope scope) {
        int index = bizScenarioScopeRepo.getParamIndex(method);
        if (index != -1) {
            return ((BizScenarioParam) args[index]).getBizScenario();
        }

        BizScenarioResolver resolver = bizScenarioScopeRepo.getResolver(scope.customResolver());
        return resolver.resolve(args);
    }
}
