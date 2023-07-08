package io.codeone.framework.ext.repo;

import io.codeone.framework.ext.session.BizScenarioResolver;

import java.lang.reflect.Method;
import java.util.function.Function;

public interface ExtensionSessionRepo {

    void computeParamIndexIfAbsent(Method method, Function<Method, Integer> func);

    int getParamIndex(Method method);

    BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> clazz);
}
