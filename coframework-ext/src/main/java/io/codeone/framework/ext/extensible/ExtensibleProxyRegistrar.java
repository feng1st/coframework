package io.codeone.framework.ext.extensible;

import io.codeone.framework.common.util.ClassUtils;
import io.codeone.framework.ext.annotation.Extension;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Registers proxy beans for all {@code Extensible} interfaces.
 *
 * <p>This class ensures that extensible interfaces are proxied and dynamically
 * routed to the appropriate {@link Extension} implementation based on a {@code
 * BizScenario}.
 */
@Component("coframeworkExtensibleProxyRegistrar")
public class ExtensibleProxyRegistrar implements BeanFactoryPostProcessor {

    private final Set<Class<?>> registered = new HashSet<>();

    /**
     * Processes the bean factory to register proxies for all extensible interfaces.
     *
     * @param beanFactory the Spring bean factory
     * @throws BeansException if any error occurs during processing
     */
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
        Class<?> extensionClass = ClassUtils.forName(extensionClassName, getClass().getClassLoader());

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
