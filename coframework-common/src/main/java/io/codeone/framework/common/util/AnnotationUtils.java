package io.codeone.framework.common.util;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class for working with annotations on methods and classes.
 */
@UtilityClass
public class AnnotationUtils {

    /**
     * Retrieves an annotation of the specified type from a method. If the annotation
     * is not present on the method, it will be searched for on the declaring class.
     *
     * @param method          the method to search for the annotation
     * @param annotationClass the class of the annotation to retrieve
     * @param <T>             the type of the annotation
     * @return the annotation instance, or {@code null} if not found
     */
    public <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass) {
        T annotation = method.getAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        return method.getDeclaringClass().getAnnotation(annotationClass);
    }

    /**
     * Retrieves all annotations present on a method and its declaring class, as
     * a map where the keys are the annotation types and the values are the annotation
     * instances.
     *
     * @param method the method to inspect
     * @return a map of annotation types to annotation instances
     */
    public Map<Class<? extends Annotation>, Annotation> getAnnotationMap(Method method) {
        Map<Class<? extends Annotation>, Annotation> map = new LinkedHashMap<>();
        Arrays.stream(method.getDeclaringClass().getAnnotations())
                .forEach(anno -> map.put(anno.annotationType(), anno));
        Arrays.stream(method.getAnnotations())
                .forEach(anno -> map.put(anno.annotationType(), anno));
        return map;
    }
}
