package io.codeone.framework.ext.repo;

import io.codeone.framework.ext.scope.BizScenarioResolver;

import java.lang.reflect.Method;
import java.util.function.Function;

public interface BizScenarioScopeRepo {

    void computeParamIndexIfAbsent(Method method, Function<Method, Integer> func);

    int getParamIndex(Method method);

    BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> clazz);
}
