package io.codeone.framework.plugin.binding.repo;

import io.codeone.framework.common.util.ClassUtils;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.binding.AnnoPluginBinding;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Scans the Spring bean factory to discover and process annotation-to-plugin bindings.
 *
 * <p>This class uses the {@link BeanFactoryPostProcessor} mechanism to locate
 * plugin beans and bind their associated annotations to the plugin classes.
 */
@Component
public class AnnoPluginBindingRepoPreloader implements BeanFactoryPostProcessor {

    @Getter
    private final List<AnnoPluginBinding> bindings = new ArrayList<>();

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

    private void process(DefaultListableBeanFactory beanFactory) {
        String[] pluginBeanNames = beanFactory.getBeanNamesForType(Plugin.class);
        for (String pluginBeanName : pluginBeanNames) {
            BeanDefinition pluginDefinition = beanFactory.getBeanDefinition(pluginBeanName);
            String pluginClassName = pluginDefinition.getBeanClassName();
            if (pluginClassName == null) {
                continue;
            }
            Class<? extends Plugin> pluginClass = ClassUtils.forName(pluginClassName, getClass().getClassLoader());

            Plug plug = pluginClass.getAnnotation(Plug.class);
            if (plug == null) {
                throw new IllegalStateException(String.format("Plugin class should be annotated with @Plug: \"%s\".",
                        pluginClass.getTypeName()));
            }

            for (Class<? extends Annotation> annoType : plug.targetAnnotations()) {
                bindings.add(AnnoPluginBinding.of(annoType, pluginClass));
            }
        }
    }
}
