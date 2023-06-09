package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.Extensible;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExtUtils {

    public static Class<?> getExtClass(Object ext) {
        if (AopUtils.isAopProxy(ext)) {
            return AopUtils.getTargetClass(ext);
        }
        return ext.getClass();
    }

    public static List<Class<?>> getAllExtensibleClasses(Class<?> extClass) {
        return Arrays.stream(ClassUtils.getAllInterfacesForClass(extClass))
                .filter(o -> AnnotationUtils.findAnnotation(o, Extensible.class) != null)
                .collect(Collectors.toList());
    }

    public static boolean isBizScenarioParam(Class<?> paramType) {
        return BizScenarioParam.class.isAssignableFrom(paramType);
    }
}
