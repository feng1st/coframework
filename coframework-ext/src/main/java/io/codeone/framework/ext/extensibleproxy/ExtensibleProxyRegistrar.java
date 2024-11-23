package io.codeone.framework.ext.extensibleproxy;

import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ExtensibleProxyRegistrar implements BeanFactoryPostProcessor {

    private final Set<Class<?>> registered = new HashSet<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            process((DefaultListableBeanFactory) beanFactory);
        }
    }

    private void process(DefaultListableBeanFactory beanFactory) {
        String[] extensionBeanNames = beanFactory.getBeanNamesForAnnotation(Extension.class);
        for (String extensionBeanName : extensionBeanNames) {
            processExtension(extensionBeanName, beanFactory);
        }
    }

    private void processExtension(String extensionBeanName, DefaultListableBeanFactory beanFactory) {
        BeanDefinition extensionBeanDefinition = beanFactory.getBeanDefinition(extensionBeanName);
        extensionBeanDefinition.setPrimary(false);

        String extensionClassName = extensionBeanDefinition.getBeanClassName();
        if (extensionClassName == null) {
            return;
        }

        Class<?> extensionClass;
        try {
            extensionClass = ClassUtils.forName(extensionClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException ignored) {
            return;
        }

        List<Class<?>> extensibleInterfaces = ExtUtils.getAllExtensibleInterfaces(extensionClass);
        for (Class<?> extensibleInterface : extensibleInterfaces) {
            registerExtensibleProxy(extensibleInterface, beanFactory);
        }
    }

    private <T> void registerExtensibleProxy(Class<T> extensibleInterface, DefaultListableBeanFactory beanFactory) {
        if (!registered.add(extensibleInterface)) {
            return;
        }

        BeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(ExtensibleProxyFactoryBean.class)
                .addConstructorArgValue(extensibleInterface)
                .addConstructorArgValue(beanFactory)
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .setPrimary(true)
                .getBeanDefinition();
        beanFactory.registerBeanDefinition(Introspector.decapitalize(extensibleInterface.getSimpleName()), beanDefinition);
    }
}
