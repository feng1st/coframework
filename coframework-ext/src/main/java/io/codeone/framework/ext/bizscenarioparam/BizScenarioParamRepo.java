package io.codeone.framework.ext.bizscenarioparam;

import java.lang.reflect.Method;

public interface BizScenarioParamRepo {

    // FIXME
    int INDEX_BY_CONTEXT = -1;

    int getParamIndex(Method method);
}
