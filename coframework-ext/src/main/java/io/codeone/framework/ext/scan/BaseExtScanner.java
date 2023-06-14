package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.util.ClassUtils;
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
        Class<?> extClass = ClassUtils.getTargetClass(ext);

        List<Class<?>> extensibleClasses = ExtUtils.getAllExtensibleClasses(extClass);
        for (Class<?> extensibleClass : extensibleClasses) {
            scanExtensible(extensibleClass);

            for (Method method : extensibleClass.getDeclaredMethods()) {
                scanExtensibleMethod(extensibleClass, method);

                Class<?> implementingClass = findImplementingClass(method, extClass);
                if (implementingClass == null) {
                    continue;
                }
                BizScenario bizScenario = findBizScenario(implementingClass, extClass);
                if (bizScenario == null) {
                    continue;
                }

                scanExtension(extensibleClass, method, implementingClass, bizScenario);
            }
        }
    }

    private Class<?> findImplementingClass(Method method, Class<?> extClass) {
        Method extMethod = ReflectionUtils.findMethod(extClass, method.getName(), method.getParameterTypes());
        if (extMethod == null) {
            return null;
        }
        return extMethod.getDeclaringClass();
    }

    private BizScenario findBizScenario(Class<?> implementingClass, Class<?> extClass) {
        Extension workingExtension = null;
        for (Class<?> clazz = extClass; clazz != null; clazz = clazz.getSuperclass()) {
            Extension extension = clazz.getAnnotation(Extension.class);
            if (extension != null) {
                workingExtension = extension;
            }
            if (clazz == implementingClass) {
                break;
            }
        }
        if (workingExtension == null) {
            return null;
        }
        return BizScenario.of(workingExtension.bizId(), workingExtension.scenario());
    }
}
