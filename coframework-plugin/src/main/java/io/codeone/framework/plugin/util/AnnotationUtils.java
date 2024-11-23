package io.codeone.framework.plugin.util;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@UtilityClass
public class AnnotationUtils {

    public <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass) {
        T annotation = method.getAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        return method.getDeclaringClass().getAnnotation(annotationClass);
    }

    public Map<Class<? extends Annotation>, Annotation> getAnnotationMap(Method method) {
        Map<Class<? extends Annotation>, Annotation> map = new LinkedHashMap<>();
        Arrays.stream(method.getDeclaringClass().getAnnotations())
                .forEach(anno -> map.put(anno.annotationType(), anno));
        Arrays.stream(method.getAnnotations())
                .forEach(anno -> map.put(anno.annotationType(), anno));
        return map;
    }
}
