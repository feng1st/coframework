package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.Extensible;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExtUtils {

    public static List<Class<?>> getAllExtensibleClasses(Class<?> extClass) {
        return Arrays.stream(ClassUtils.getAllInterfacesForClass(extClass))
                .filter(o -> AnnotationUtils.findAnnotation(o, Extensible.class) != null)
                .collect(Collectors.toList());
    }

    public static boolean isBizScenarioParam(Class<?> paramType) {
        return BizScenarioParam.class.isAssignableFrom(paramType);
    }

    public static String getClassKey(Class<?> clazz) {
        return clazz.getName();
    }

    public static String getClassKey(Method method) {
        return getClassKey(method.getDeclaringClass());
    }

    public static String getMethodSubKey(Method method) {
        return method.getName()
                + "("
                + Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(","))
                + ")";
    }
}
