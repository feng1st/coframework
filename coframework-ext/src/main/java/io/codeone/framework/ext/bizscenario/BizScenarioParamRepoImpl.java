package io.codeone.framework.ext.bizscenario;

import io.codeone.framework.ext.annotation.Extension;
import io.codeone.framework.ext.util.ExtUtils;
import io.codeone.framework.plugin.util.ClassUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Component
public class BizScenarioParamRepoImpl implements BeanFactoryPostProcessor, BizScenarioParamRepo {

    private final Set<Class<?>> processed = new HashSet<>();

    private final Map<Method, Integer> map = new HashMap<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            process((DefaultListableBeanFactory) beanFactory);
        }
    }

    @Override
    public int getParamIndex(Method method) {
        Integer index = map.get(method);
        if (index == null) {
            throw new IllegalStateException(String.format(
                    "No BizScenario source registered for method '%s'",
                    method));
        }
        return index;
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
            processExtensible(extensibleInterface);
        }
    }

    private void processExtensible(Class<?> extensibleInterface) {
        if (!processed.add(extensibleInterface)) {
            return;
        }

        for (Method method : extensibleInterface.getMethods()) {
            if (method.getDeclaringClass() == Object.class) {
                continue;
            }
            map.computeIfAbsent(method, k -> BizScenarioParamParser.parseParamIndex(extensibleInterface, method));
        }
    }
}
