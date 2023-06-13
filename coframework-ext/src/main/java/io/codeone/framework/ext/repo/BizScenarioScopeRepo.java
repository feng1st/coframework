package io.codeone.framework.ext.repo;

import io.codeone.framework.ext.scope.BizScenarioResolver;

import java.lang.reflect.Method;

public interface BizScenarioScopeRepo {

    void putParamIndex(Method method, int index);

    int getParamIndex(Method method);

    BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> clazz);
}
