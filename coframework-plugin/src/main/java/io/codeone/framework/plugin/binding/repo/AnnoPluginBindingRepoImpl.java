package io.codeone.framework.plugin.binding.repo;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.binding.AnnoPluginBinding;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@link AnnoPluginBindingRepo} that dynamically processes and
 * binds annotations to plugins.
 *
 * <p>This class is the first step in the bean lifecycle related to plugin bindings.
 * It ensures that annotation-to-plugin mappings are established before the pointcut
 * matching and post-processing logic execute. This guarantees that {@code PluginPointcut#matches}
 * and {@code PluginInterceptorPostProcessor#postProcessAfterInitialization} can
 * work as expected.
 */
@Component
public class AnnoPluginBindingRepoImpl implements InitializingBean, AnnoPluginBindingRepo {

    private final Map<Class<? extends Annotation>, Set<Class<? extends Plugin>>> map = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AnnoPluginBindingRepoPreloader annoPluginBindingRepoPreloader;

    /**
     * Initializes the static bindings and loads plugins from the application context.
     *
     * @throws Exception if an error occurs during initialization
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        STATIC_BINDINGS
                .forEach(binding -> bind(binding.getAnnoType(), binding.getPluginClass()));
        applicationContext.getBeansOfType(AnnoPluginBinding.class).values()
                .forEach(binding -> bind(binding.getAnnoType(), binding.getPluginClass()));
        annoPluginBindingRepoPreloader.getBindings()
                .forEach(binding -> bind(binding.getAnnoType(), binding.getPluginClass()));
    }

    /**
     * Retrieves the set of plugin classes bound to a specific annotation type.
     *
     * @param annoType the annotation type to query
     * @return a set of plugin classes associated with the annotation type
     */
    @Override
    public Set<Class<? extends Plugin>> getPluginClasses(Class<? extends Annotation> annoType) {
        return map.get(annoType);
    }

    private void bind(Class<? extends Annotation> annoType, Class<? extends Plugin> pluginClass) {
        map.computeIfAbsent(annoType, k -> new HashSet<>())
                .add(pluginClass);
    }
}
