package io.codeone.framework.ext.repo.impl;

import io.codeone.framework.ext.repo.ExtensionSessionRepo;
import io.codeone.framework.ext.session.BizScenarioResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * {@inheritDoc}
 */
@Repository
public class ExtensionSessionRepoImpl implements ExtensionSessionRepo {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<Method, Integer> paramIndexMap = new HashMap<>();

    private final Map<Class<? extends BizScenarioResolver>, BizScenarioResolver> resolverMap
            = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void computeParamIndexIfAbsent(Method method, Supplier<Integer> supplier) {
        paramIndexMap.computeIfAbsent(method, k -> supplier.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getParamIndex(Method method) {
        Integer index = paramIndexMap.get(method);
        if (index == null) {
            throw new IllegalStateException("Looking for BizScenarioParam on an unregistered method '" + method + "'");
        }
        return index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> clazz) {
        try {
            return resolverMap.computeIfAbsent(clazz, k -> applicationContext.getBean(k));
        } catch (Exception e) {
            throw new IllegalStateException("Could not load BizScenarioResolver '" + clazz.getSimpleName() + "'");
        }
    }
}
