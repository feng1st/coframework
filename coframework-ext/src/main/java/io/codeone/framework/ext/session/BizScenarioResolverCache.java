package io.codeone.framework.ext.session;

import io.codeone.framework.common.util.TypeStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Caches and retrieves {@link BizScenarioResolver} beans for resolving {@code BizScenario}.
 *
 * <p>Uses Spring's {@link ApplicationContext} to load resolver beans dynamically.
 */
@Component
public class BizScenarioResolverCache {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<Class<? extends BizScenarioResolver>, BizScenarioResolver> cache
            = new ConcurrentHashMap<>();

    /**
     * Retrieves the resolver instance for the specified class.
     *
     * @param resolverClass the resolver class
     * @return the {@link BizScenarioResolver} instance
     * @throws IllegalStateException if the resolver cannot be loaded
     */
    public BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> resolverClass) {
        try {
            return cache.computeIfAbsent(resolverClass, k -> applicationContext.getBean(k));
        } catch (Exception e) {
            throw new IllegalStateException(String.format(
                    "Failed to load BizScenarioResolver \"%s\". Ensure the resolver is properly registered as a Spring bean.",
                    TypeStringUtils.toString(resolverClass)), e);
        }
    }
}
