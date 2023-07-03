package io.codeone.framework.ext.scope;

import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;

public interface BizScenarioScopeIndexer {

    void index(Method method, BizScenarioScope annotation);

    BizScenario resolve(Method method, Object[] args, BizScenarioScope scope);
}
