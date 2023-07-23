package io.codeone.framework.ext.repo;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public interface BizScenarioParamRepo {

    void computeParamIndexIfAbsent(Method method, Supplier<Integer> supplier);

    int getParamIndex(Method method);
}
