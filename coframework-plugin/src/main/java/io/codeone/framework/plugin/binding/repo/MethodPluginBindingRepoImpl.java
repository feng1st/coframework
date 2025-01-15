package io.codeone.framework.plugin.binding.repo;

import io.codeone.framework.common.util.AnnotationUtils;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.binding.EnablePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@link MethodPluginBindingRepo} for dynamically managing
 * method-to-plugin bindings.
 */
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class MethodPluginBindingRepoImpl implements MethodPluginBindingRepo {

    @Autowired
    private AnnoPluginBindingRepo annoPluginBindingRepo;

    private final Map<Method, Set<Class<? extends Plugin>>> map = new ConcurrentHashMap<>();

    /**
     * Dynamically binds plugins to a method based on its annotations and explicitly
     * enabled plugins.
     *
     * @param method the method to bind plugins to
     * @return {@code true} if plugins were bound, {@code false} otherwise
     */
    @Override
    public boolean dynamicBind(Method method) {
        Map<Class<? extends Annotation>, Annotation> annoMap = AnnotationUtils.getAnnotationMap(method);

        for (Annotation annotation : annoMap.values()) {
            Set<Class<? extends Plugin>> set = annoPluginBindingRepo.getPluginClasses(annotation.annotationType());
            if (set != null) {
                map.computeIfAbsent(method, k -> new LinkedHashSet<>())
                        .addAll(set);
            }
        }

        EnablePlugin enablePlugin = (EnablePlugin) annoMap.get(EnablePlugin.class);
        if (enablePlugin != null) {
            map.computeIfAbsent(method, k -> new LinkedHashSet<>())
                    .addAll(Arrays.asList(enablePlugin.value()));
        }

        return map.containsKey(method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Class<? extends Plugin>> getPluginClasses(Method method) {
        return map.get(method);
    }
}
