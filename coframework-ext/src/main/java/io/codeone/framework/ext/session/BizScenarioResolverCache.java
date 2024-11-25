package io.codeone.framework.ext.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BizScenarioResolverCache {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<Class<? extends BizScenarioResolver>, BizScenarioResolver> cache
            = new ConcurrentHashMap<>();

    public BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> resolverClass) {
        try {
            return cache.computeIfAbsent(resolverClass, k -> applicationContext.getBean(k));
        } catch (Exception e) {
            throw new IllegalStateException(String.format(
                    "Cannot load BizScenarioResolver '%s'",
                    resolverClass.getSimpleName()));
        }
    }
}
