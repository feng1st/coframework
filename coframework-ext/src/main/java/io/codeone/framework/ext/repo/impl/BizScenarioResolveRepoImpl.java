package io.codeone.framework.ext.repo.impl;

import io.codeone.framework.ext.repo.BizScenarioResolveRepo;
import io.codeone.framework.ext.resolve.BizScenarioResolver;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BizScenarioResolveRepoImpl implements BizScenarioResolveRepo {

    private final Map<Method, Integer> paramIndexMap = new ConcurrentHashMap<>();

    private final Map<Class<? extends BizScenarioResolver>, BizScenarioResolver> resolverMap = new ConcurrentHashMap<>();

    @Override
    public void putParamIndex(Method method, int index) {
        paramIndexMap.put(method, index);
    }

    @Override
    public int getParamIndex(Method method) {
        Integer index = paramIndexMap.get(method);
        if (index == null) {
            throw new IllegalStateException("Looking for BizScenarioParam on an unregistered method '" + method + "'");
        }
        return index;
    }

    @Override
    public void putResolver(Class<? extends BizScenarioResolver> clazz, BizScenarioResolver resolver) {
        resolverMap.put(clazz, resolver);
    }

    @Override
    public BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> clazz) {
        BizScenarioResolver resolver = resolverMap.get(clazz);
        if (resolver == null) {
            throw new IllegalStateException("Looking for an unregistered BizScenarioResolver '"
                    + clazz.getSimpleName() + "'");
        }
        return resolver;
    }
}
