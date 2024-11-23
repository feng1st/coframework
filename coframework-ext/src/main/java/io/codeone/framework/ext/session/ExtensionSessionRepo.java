package io.codeone.framework.ext.session;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public interface ExtensionSessionRepo {

    void computeParamIndexIfAbsent(Method method, Supplier<Integer> supplier);

    int getParamIndex(Method method);

    BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> clazz);
}
