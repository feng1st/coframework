package io.codeone.framework.ext.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Repository
public class ExtensionSessionRepoImpl implements ExtensionSessionRepo {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<Method, Integer> paramIndexMap = new HashMap<>();

    private final Map<Class<? extends BizScenarioResolver>, BizScenarioResolver> resolverMap
            = new ConcurrentHashMap<>();

    @Override
    public void computeParamIndexIfAbsent(Method method, Supplier<Integer> supplier) {
        paramIndexMap.computeIfAbsent(method, k -> supplier.get());
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
