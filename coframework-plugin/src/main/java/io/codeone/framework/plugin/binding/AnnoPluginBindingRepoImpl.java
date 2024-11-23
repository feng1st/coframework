package io.codeone.framework.plugin.binding;

import io.codeone.framework.plugin.AnnoPluginBinding;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AnnoPluginBindingRepoImpl implements BeanFactoryPostProcessor, AnnoPluginBindingRepo {

    private final Map<Class<? extends Annotation>, Set<Class<? extends Plugin>>> map = new ConcurrentHashMap<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            process((DefaultListableBeanFactory) beanFactory);
        }
    }

    @Override
    public Set<Class<? extends Plugin>> getPluginClasses(Class<? extends Annotation> annoType) {
        return map.get(annoType);
    }

    private void process(DefaultListableBeanFactory beanFactory) {
        String[] pluginBeanNames = beanFactory.getBeanNamesForType(Plugin.class);
        for (String pluginBeanName : pluginBeanNames) {
            BeanDefinition pluginDefinition = beanFactory.getBeanDefinition(pluginBeanName);
            String pluginClassName = pluginDefinition.getBeanClassName();
            Class<? extends Plugin> pluginClass;
            try {
                pluginClass = (Class<? extends Plugin>) ClassUtils.forName(pluginClassName, getClass().getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }

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
