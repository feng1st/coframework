package io.codeone.framework.ext.repo;

import java.lang.reflect.Method;

public interface BizScenarioParamRepo {

    void putParamIndex(Method method, int index);

    int getParamIndex(Method method);
}
