package io.codeone.framework.ext.repo;

import java.lang.reflect.Method;
import java.util.function.Function;

public interface BizScenarioParamRepo {

    void computeParamIndexIfAbsent(Method method, Function<Method, Integer> func);

    int getParamIndex(Method method);
}
