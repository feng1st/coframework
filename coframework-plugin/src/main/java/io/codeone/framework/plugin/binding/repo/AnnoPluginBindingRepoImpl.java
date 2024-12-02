package io.codeone.framework.plugin.binding.repo;

import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.binding.AnnoPluginBinding;
import io.codeone.framework.plugin.util.ClassUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
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
 *
 * <p>Uses the Spring {@link BeanFactoryPostProcessor} mechanism to perform its
 * initialization.
 */
@Component
public class AnnoPluginBindingRepoImpl implements BeanFactoryPostProcessor, AnnoPluginBindingRepo {

    private final Map<Class<? extends Annotation>, Set<Class<? extends Plugin>>> map = new ConcurrentHashMap<>();

    /**
     * Processes the bean factory to discover plugins and build annotation-to-plugin
     * bindings.
     *
     * @param beanFactory the Spring bean factory
     * @throws BeansException if any bean-related error occurs
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            process((DefaultListableBeanFactory) beanFactory);
        }
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

    private void process(DefaultListableBeanFactory beanFactory) {
        String[] pluginBeanNames = beanFactory.getBeanNamesForType(Plugin.class);
        for (String pluginBeanName : pluginBeanNames) {
            BeanDefinition pluginDefinition = beanFactory.getBeanDefinition(pluginBeanName);
            String pluginClassName = pluginDefinition.getBeanClassName();
            Class<? extends Plugin> pluginClass = ClassUtils.forName(pluginClassName, getClass().getClassLoader());

            Plug plug = pluginClass.getAnnotation(Plug.class);
            if (plug == null) {
                return;
            }

            for (Class<? extends Annotation> annoType : plug.targetAnnotations()) {
                bind(annoType, pluginClass);
            }
        }

        STATIC_BINDINGS
                .forEach(binding -> bind(binding.getAnnoType(), binding.getPluginClass()));
        beanFactory.getBeansOfType(AnnoPluginBinding.class).values()
                .forEach(binding -> bind(binding.getAnnoType(), binding.getPluginClass()));
    }

    private void bind(Class<? extends Annotation> annoType, Class<? extends Plugin> pluginClass) {
        map.computeIfAbsent(annoType, k -> new HashSet<>())
                .add(pluginClass);
    }
}
