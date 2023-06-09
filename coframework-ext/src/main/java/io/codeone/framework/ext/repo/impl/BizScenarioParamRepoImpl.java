package io.codeone.framework.ext.repo.impl;

import io.codeone.framework.ext.repo.BizScenarioParamRepo;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Repository
public class BizScenarioParamRepoImpl implements BizScenarioParamRepo {

    private final Map<Method, Integer> map = new HashMap<>();

    @Override
    public void computeParamIndexIfAbsent(Method method, Function<Method, Integer> func) {
        map.computeIfAbsent(method, func);
    }

    @Override
    public int getParamIndex(Method method) {
        Integer index = map.get(method);
        if (index == null) {
            throw new IllegalStateException("Looking for BizScenarioParam on an unregistered method '" + method + "'");
        }
        return index;
    }
}
