package io.codeone.framework.plugin.binding.repo;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.binding.EnablePlugin;
import io.codeone.framework.plugin.util.AnnotationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MethodPluginBindingRepoImpl implements MethodPluginBindingRepo {

    @Autowired
    private AnnoPluginBindingRepo annoPluginBindingRepo;

    private final Map<Method, Set<Class<? extends Plugin>>> map = new ConcurrentHashMap<>();

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

    @Override
    public Set<Class<? extends Plugin>> getPluginClasses(Method method) {
        return map.get(method);
    }
}
