package io.codeone.framework.ext.repo.impl;

import io.codeone.framework.ext.repo.BizScenarioParamRepo;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * {@inheritDoc}
 */
@Repository
public class BizScenarioParamRepoImpl implements BizScenarioParamRepo {

    private final Map<Method, Integer> map = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void computeParamIndexIfAbsent(Method method, Supplier<Integer> supplier) {
        map.computeIfAbsent(method, k -> supplier.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getParamIndex(Method method) {
        Integer index = map.get(method);
        if (index == null) {
            throw new IllegalStateException("Looking for BizScenarioParam on an unregistered method '" + method + "'");
        }
        return index;
    }
}
