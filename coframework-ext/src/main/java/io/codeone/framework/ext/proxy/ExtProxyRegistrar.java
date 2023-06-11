package io.codeone.framework.ext.proxy;

import io.codeone.framework.ext.Extensible;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ExtProxyRegistrar implements BeanFactoryPostProcessor {

    public static final String PREFIX = "extProxy$";

    private final Set<Class<?>> set = new HashSet<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (!(beanFactory instanceof DefaultListableBeanFactory)) {
            return;
        }
        registerAllProxies((DefaultListableBeanFactory) beanFactory);
    }

    private <A extends Annotation> void registerAllProxies(DefaultListableBeanFactory beanFactory) {
        String[] extBeanNames = beanFactory.getBeanNamesForAnnotation(Extensible.class);
        for (String extBeanName : extBeanNames) {
            if (extBeanName.startsWith(PREFIX)) {
                continue;
            }

            BeanDefinition extBeanDefinition = beanFactory.getBeanDefinition(extBeanName);
            extBeanDefinition.setPrimary(false);

            Class<?> extClass = getExtClass(extBeanDefinition.getBeanClassName());
            List<Class<?>> extensibleClasses = ExtUtils.getAllExtensibleClasses(extClass);
            if (extensibleClasses.isEmpty()) {
                continue;
            }

            for (Class<?> extensibleClass : extensibleClasses) {
                registerProxy(beanFactory, extensibleClass);
            }
        }
    }

    private <T> void registerProxy(BeanDefinitionRegistry registry, Class<T> extensibleClass) {
        if (!set.add(extensibleClass)) {
            return;
        }

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(extensibleClass)
                .setFactoryMethodOnBean(ExtProxyFactory.FACTORY_METHOD_NAME, ExtProxyFactory.FACTORY_BEAN_NAME)
                .addConstructorArgValue(extensibleClass)
                .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                .setPrimary(true)
                .getBeanDefinition();
        registry.registerBeanDefinition(PREFIX + extensibleClass.getSimpleName(), beanDefinition);
    }

    private Class<?> getExtClass(String extClassName) {
        try {
            return Class.forName(extClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
