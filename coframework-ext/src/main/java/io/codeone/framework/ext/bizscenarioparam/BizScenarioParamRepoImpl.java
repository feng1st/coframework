package io.codeone.framework.ext.bizscenarioparam;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.RouteBy;
import io.codeone.framework.ext.RouteByContext;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Component
public class BizScenarioParamRepoImpl implements BeanFactoryPostProcessor, BizScenarioParamRepo {

    private final Set<Class<?>> processedExtensibleInterfaces = new HashSet<>();

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
                    "Looking for BizScenarioParam on an unregistered method '%s'",
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

        Class<?> extensionClass;
        try {
            extensionClass = ClassUtils.forName(extensionClassName, getClass().getClassLoader());
        } catch (ClassNotFoundException ignored) {
            return;
        }

        List<Class<?>> extensibleInterfaces = ExtUtils.getAllExtensibleInterfaces(extensionClass);
        for (Class<?> extensibleInterface : extensibleInterfaces) {
            processExtensible(extensibleInterface);
        }
    }

    private void processExtensible(Class<?> extensibleInterface) {
        if (!processedExtensibleInterfaces.add(extensibleInterface)) {
            return;
        }

        for (Method method : extensibleInterface.getMethods()) {
            if (method.getDeclaringClass() == Object.class) {
                continue;
            }
            map.computeIfAbsent(method, k -> parseBizScenarioParamIndex(extensibleInterface, method));
        }
    }

    private int parseBizScenarioParamIndex(Class<?> extensibleInterface, Method method) {
        if (method.getParameters().length == 0) {
            return -1;
        }

        Integer index = null;
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter param = method.getParameters()[i];
            if (param.isAnnotationPresent(RouteBy.class)) {
                if (index != null) {
                    throw new IllegalStateException(String.format(
                            "Found duplicate @RouteBy on '%s'",
                            method));
                }
                if (!BizScenarioParam.class.isAssignableFrom(param.getType())) {
                    throw new IllegalStateException(String.format(
                            "The parameter of '%s' annotated by @RouteBy is not a BizScenarioParam",
                            method));
                }
                index = i;
            }
        }
        if (index != null) {
            if (method.isAnnotationPresent(RouteByContext.class)) {
                throw new IllegalStateException(String.format(
                        "Found both @RouteBy and @RouteByContext on '%s'",
                        method));
            }
            return index;
        }

        if (method.isAnnotationPresent(RouteByContext.class)
                || extensibleInterface.isAnnotationPresent(RouteByContext.class)) {
            return -1;
        }

        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter param = method.getParameters()[i];
            if (BizScenarioParam.class.isAssignableFrom(param.getType())) {
                if (index != null) {
                    throw new IllegalStateException(String.format(
                            "Found duplicate BizScenarioParams on '%s'",
                            method));
                }
                index = i;
            }
        }
        if (index != null) {
            return index;
        }

        return -1;
    }
}
