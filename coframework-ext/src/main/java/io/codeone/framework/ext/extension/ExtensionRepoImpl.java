package io.codeone.framework.ext.extension;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.util.ClassUtils;
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

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansWithAnnotation(Extension.class).values()
                .forEach(this::registerExtension);
    }

    @Override
    public Object getExtension(Class<?> extensibleInterface, BizScenario bizScenario) {
        CacheKey cacheKey = CacheKey.of(extensibleInterface, bizScenario);
        Optional<Object> extension = cache.get(cacheKey);
        if (extension == null
                || !extension.isPresent()) {
            throw new IllegalArgumentException(String.format(
                    "Could not find Extension for '%s'",
                    cacheKey));
        }
        return extension.orElse(null);
    }

    private void registerExtension(Object extension) {
        Class<?> extensionClass = ClassUtils.getTargetClass(extension);

        List<Class<?>> extensibleInterfaces = ExtUtils.getAllExtensibleInterfaces(extensionClass);
        if (extensibleInterfaces.isEmpty()) {
            throw new IllegalStateException(String.format(
                    "'%s' did not extend any Extensible interface (which annotated by @Ability or @ExtensionPoint)",
                    extensionClass));
        }

        Extension extensionAnno = extensionClass.getAnnotation(Extension.class);
        List<BizScenario> bizScenarios = new ArrayList<>();
        if (extensionAnno.scenarios().length > 0) {
            Arrays.stream(extensionAnno.scenarios())
                    .forEach(o -> bizScenarios.add(BizScenario.of(extensionAnno.bizId(), o)));
        } else {
            bizScenarios.add(BizScenario.of(extensionAnno.bizId(), extensionAnno.scenario()));
        }

        for (Class<?> extensibleInterface : extensibleInterfaces) {
            for (BizScenario bizScenario : bizScenarios) {
                registerExtension(extensibleInterface, bizScenario, extension);
            }
        }
    }

    private void registerExtension(Class<?> extensibleInterface, BizScenario bizScenario, Object extension) {
        Object existing = map.computeIfAbsent(extensibleInterface, k -> new HashMap<>())
                .put(bizScenario, extension);
        if (existing != null) {
            throw new IllegalStateException(String.format(
                    "Found duplicate Extension for '%s': ['%s', '%s']",
                    extensibleInterface,
                    existing.getClass().getSimpleName(),
                    existing.getClass().getSimpleName()));
        }
    }

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
