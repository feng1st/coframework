package io.codeone.framework.ext.repo.impl;

import io.codeone.framework.ext.repo.BizScenarioScopeRepo;
import io.codeone.framework.ext.scope.BizScenarioResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BizScenarioScopeRepoImpl implements BizScenarioScopeRepo {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<Method, Integer> paramIndexMap = new HashMap<>();

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
    public BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> clazz) {
        try {
            return resolverMap.computeIfAbsent(clazz, k -> applicationContext.getBean(k));
        } catch (Exception e) {
            throw new IllegalStateException("Could not load BizScenarioResolver '" + clazz.getSimpleName() + "'");
        }
    }
}
