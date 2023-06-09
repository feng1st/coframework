package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseExtScanner implements ExtScanner {

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansWithAnnotation(Extension.class).forEach(this::scan);
    }

    private void scan(String beanName, Object ext) {
        Class<?> extClass = ExtUtils.getExtClass(ext);

        List<Class<?>> extensibleClasses = ExtUtils.getAllExtensibleClasses(extClass);
        for (Class<?> extensibleClass : extensibleClasses) {
            scanExtensible(extensibleClass);

            for (Method method : extensibleClass.getDeclaredMethods()) {
                scanExtensibleMethod(extensibleClass, method);

                Class<?> methodDeclaringClass = findMethodDeclaringClass(method, extClass);
                if (methodDeclaringClass == null) {
                    continue;
                }
                BizScenario methodBizScenario = findMethodBizScenario(methodDeclaringClass, extClass);
                if (methodBizScenario == null) {
                    continue;
                }

                scanExtension(extensibleClass, method, methodDeclaringClass, methodBizScenario);
            }
        }
    }

    private Class<?> findMethodDeclaringClass(Method method, Class<?> extClass) {
        Method extMethod = ReflectionUtils.findMethod(extClass, method.getName(), method.getParameterTypes());
        if (extMethod == null) {
            return null;
        }
        return extMethod.getDeclaringClass();
    }

    private BizScenario findMethodBizScenario(Class<?> methodDeclaringClass, Class<?> extClass) {
        Extension workingExtension = null;
        for (Class<?> clazz = extClass; clazz != null; clazz = clazz.getSuperclass()) {
            Extension extension = clazz.getAnnotation(Extension.class);
            if (extension != null) {
                workingExtension = extension;
            }
            if (clazz == methodDeclaringClass) {
                break;
            }
        }
        if (workingExtension == null) {
            return null;
        }
        return BizScenario.of(workingExtension.bizId(), workingExtension.scenario());
    }
}
