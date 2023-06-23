package io.codeone.framework.intercept.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class MethodWrap {

    private final Method method;

    private Class<?>[] parameterTypes;

    private String[] parameterNames;

    private Map<Class<? extends Annotation>, Annotation> annotations;

    private Map<Class<? extends Annotation>, Annotation> methodAnnotations;

    private Map<Class<? extends Annotation>, Annotation> classAnnotations;

    public MethodWrap(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }

    public Class<?>[] getParameterTypes() {
        Class<?>[] paramTypes;
        if ((paramTypes = parameterTypes) == null) {
            synchronized (this) {
                if ((paramTypes = parameterTypes) == null) {
                    paramTypes = method.getParameterTypes();
                    parameterTypes = paramTypes;
                }
            }
        }
        return paramTypes;
    }

    public String[] getParameterNames() {
        String[] paramNames;
        if ((paramNames = parameterNames) == null) {
            synchronized (this) {
                if ((paramNames = parameterNames) == null) {
                    Parameter[] params = method.getParameters();
                    paramNames = new String[params.length];
                    for (int i = 0; i < params.length; i++) {
                        paramNames[i] = params[i].getName();
                    }
                }
                parameterNames = paramNames;
            }
        }
        return paramNames;
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public boolean isAnnotationPresent(
            Class<? extends Annotation> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(getAnnotations().get(annotationClass));
    }

    public boolean isMethodAnnotationPresent(
            Class<? extends Annotation> annotationClass) {
        return getMethodAnnotation(annotationClass) != null;
    }

    public <T extends Annotation> T getMethodAnnotation(
            Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(getMethodAnnotations().get(
                annotationClass));
    }

    public boolean isClassAnnotationPresent(
            Class<? extends Annotation> annotationClass) {
        return getClassAnnotation(annotationClass) != null;
    }

    public <T extends Annotation> T getClassAnnotation(
            Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(getClassAnnotations().get(
                annotationClass));
    }

    private Map<Class<? extends Annotation>, Annotation> getAnnotations() {
        Map<Class<? extends Annotation>, Annotation> annos;
        if ((annos = annotations) == null) {
            synchronized (this) {
                if ((annos = annotations) == null) {
                    annos = new LinkedHashMap<>();
                    for (Annotation anno : method.getAnnotations()) {
                        annos.put(anno.annotationType(), anno);
                    }
                    Class<?> clazz = method.getDeclaringClass();
                    for (Annotation anno : clazz.getAnnotations()) {
                        annos.putIfAbsent(anno.annotationType(), anno);
                    }
                    annotations = annos;
                }
            }
        }
        return annos;
    }

    private Map<Class<? extends Annotation>, Annotation>
    getMethodAnnotations() {
        Map<Class<? extends Annotation>, Annotation> annos;
        if ((annos = methodAnnotations) == null) {
            synchronized (this) {
                if ((annos = methodAnnotations) == null) {
                    annos = new LinkedHashMap<>();
                    for (Annotation anno : method.getAnnotations()) {
                        annos.put(anno.annotationType(), anno);
                    }
                    methodAnnotations = annos;
                }
            }
        }
        return annos;
    }

    private Map<Class<? extends Annotation>, Annotation>
    getClassAnnotations() {
        Map<Class<? extends Annotation>, Annotation> annos;
        if ((annos = classAnnotations) == null) {
            synchronized (this) {
                if ((annos = classAnnotations) == null) {
                    annos = new LinkedHashMap<>();
                    Class<?> clazz = method.getDeclaringClass();
                    for (Annotation anno : clazz.getAnnotations()) {
                        annos.put(anno.annotationType(), anno);
                    }
                    classAnnotations = annos;
                }
            }
        }
        return annos;
    }
}
