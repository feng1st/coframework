package io.codeone.framework.ext.proxy;

import io.codeone.framework.ext.Extensible;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExtProxyRegistrar implements ImportBeanDefinitionRegistrar {

    public static final String PREFIX = "extProxy$";

    private final Set<Class<?>> set = new HashSet<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (!(registry instanceof DefaultListableBeanFactory)) {
            return;
        }
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
        registerAllProxies(beanFactory);
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

    private <T> void registerProxy(DefaultListableBeanFactory beanFactory, Class<T> extensibleClass) {
        if (!set.add(extensibleClass)) {
            return;
        }

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(ExtProxyBeanFactory.class)
                .setFactoryMethod(ExtProxyBeanFactory.FACTORY_METHOD_NAME)
                .addConstructorArgValue(beanFactory)
                .addConstructorArgValue(extensibleClass)
                .setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                .setPrimary(true)
                .getBeanDefinition();
        beanFactory.registerBeanDefinition(PREFIX + extensibleClass.getSimpleName(), beanDefinition);
    }

    private Class<?> getExtClass(String extClassName) {
        try {
            return Class.forName(extClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
