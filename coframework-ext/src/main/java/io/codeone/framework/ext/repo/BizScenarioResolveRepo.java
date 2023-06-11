package io.codeone.framework.ext.repo;

import io.codeone.framework.ext.resolve.BizScenarioResolver;

import java.lang.reflect.Method;

public interface BizScenarioResolveRepo {

    void putParamIndex(Method method, int index);

    int getParamIndex(Method method);

    void putResolver(Class<? extends BizScenarioResolver> clazz, BizScenarioResolver resolver);

    BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> clazz);
}
