package io.codeone.framework.ext.extension;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.codeone.framework.common.util.ClassUtils;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.annotation.Extension;
import io.codeone.framework.ext.util.ExtUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ExtensionRepo} that manages and resolves {@link Extension}
 * instances.
 *
 * <p>This implementation uses an internal cache for efficient lookup and supports
 * hierarchical resolution of {@link BizScenario} levels.
 */
@Component
public class ExtensionRepoImpl implements InitializingBean, ExtensionRepo {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<Class<?>, Map<BizScenario, Object>> map = new HashMap<>();

    private final LoadingCache<CacheKey, Optional<Object>> cache = Caffeine.newBuilder()
            .expireAfterAccess(24, TimeUnit.HOURS)
            .build(k -> {
                Map<BizScenario, Object> subMap = map.get(k.getExtensibleInterface());
                if (CollectionUtils.isEmpty(subMap)) {
                    return Optional.empty();
                }
                for (BizScenario bizScenario : k.getBizScenario()) {
                    Object extension = subMap.get(bizScenario);
                    if (extension != null) {
                        return Optional.of(extension);
                    }
                }
                return Optional.empty();
            });

    /**
     * Registers all {@link Extension} beans found in the application context.
     *
     * <p>This method is invoked during the initialization phase to populate the
     * repository with all available extensions.
     *
     * @throws Exception if an error occurs during registration
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansWithAnnotation(Extension.class).values()
                .forEach(this::register);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Retrieves the {@link Extension} by consulting the internal cache. If no
     * extension is found for the specified {@link BizScenario}, an exception is
     * thrown.
     */
    @Override
    public Object getExtension(Class<?> extensibleInterface, BizScenario bizScenario) {
        CacheKey cacheKey = CacheKey.of(extensibleInterface, bizScenario);
        Optional<Object> extension = cache.get(cacheKey);
        if (extension == null
                || !extension.isPresent()) {
            throw new IllegalArgumentException(String.format(
                    "No Extension found for '%s'",
                    cacheKey));
        }
        return extension.get();
    }

    /**
     * Registers a single {@link Extension} instance with the repository.
     *
     * @param extension the extension instance to register
     * @throws IllegalStateException if the extension does not implement an {@code
     *                               Extensible} interface or if there is a conflict
     *                               with an existing extension
     */
    private void register(Object extension) {
        Class<?> extensionClass = ClassUtils.getTargetClass(extension);

        List<Class<?>> extensibleInterfaces = ExtUtils.getAllExtensibleInterfaces(extensionClass);
        if (extensibleInterfaces.isEmpty()) {
            throw new IllegalStateException(String.format(
                    "'%s' does not implement any Extensible interface (annotated with @Ability or @ExtensionPoint)",
                    extensionClass.getSimpleName()));
        }

        Extension extensionAnno = extensionClass.getAnnotation(Extension.class);
        List<BizScenario> bizScenarios = Arrays.stream(extensionAnno.scenarios())
                .map(o -> BizScenario.of(extensionAnno.bizId(), o))
                .collect(Collectors.toList());

        for (Class<?> extensibleInterface : extensibleInterfaces) {
            for (BizScenario bizScenario : bizScenarios) {
                registerExtension(extensibleInterface, bizScenario, extension);
            }
        }
    }

    /**
     * Registers an extension for a specific {@code Extensible} interface and {@link
     * BizScenario}.
     *
     * @param extensibleInterface the {@code Extensible} interface
     * @param bizScenario         the {@link BizScenario} to associate with the
     *                            extension
     * @param extension           the extension instance
     * @throws IllegalStateException if a duplicate extension is found for the same
     *                               {@link BizScenario}
     */
    private void registerExtension(Class<?> extensibleInterface, BizScenario bizScenario, Object extension) {
        Object existing = map.computeIfAbsent(extensibleInterface, k -> new HashMap<>())
                .put(bizScenario, extension);
        if (existing != null) {
            throw new IllegalStateException(String.format(
                    "Duplicate Extension found for '%s': existing '%s' vs new '%s'",
                    extensibleInterface.getSimpleName(),
                    ClassUtils.getTargetClass(existing).getSimpleName(),
                    ClassUtils.getTargetClass(extension).getSimpleName()));
        }
    }

    /**
     * Represents a unique key for caching {@link Extension} instances.
     */
    @Data
    @RequiredArgsConstructor(staticName = "of")
    private static class CacheKey {

        private final Class<?> extensibleInterface;

        private final BizScenario bizScenario;

        @Override
        public String toString() {
            return extensibleInterface.getSimpleName() + "[" + bizScenario + "]";
        }
    }
}
