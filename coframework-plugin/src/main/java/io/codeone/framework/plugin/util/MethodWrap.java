package io.codeone.framework.plugin.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A snapshot of a method and its commonly used properties.
 * <p>
 * Please do not modify any of these properties since they are globally cached.
 */
public class MethodWrap {

    private static final Map<Method, MethodWrap> CACHE = new ConcurrentHashMap<>();

    private final Method method;

    private Class<?>[] parameterTypes;

    private String[] parameterNames;

    private Class<?>[] exceptionTypes;

    private Map<Class<? extends Annotation>, Annotation> annotations;

    private Map<Class<? extends Annotation>, Annotation> methodAnnotations;

    private Map<Class<? extends Annotation>, Annotation> classAnnotations;

    public static MethodWrap of(Method method) {
        return CACHE.computeIfAbsent(method, MethodWrap::new);
    }

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
        return cacheAndReturn(() -> parameterTypes, o -> parameterTypes = o, method::getParameterTypes);
    }

    public String[] getParameterNames() {
        return cacheAndReturn(() -> parameterNames, o -> parameterNames = o, () ->
                Arrays.stream(method.getParameters()).map(Parameter::getName).toArray(String[]::new));
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public Class<?>[] getExceptionTypes() {
        return cacheAndReturn(() -> exceptionTypes, o -> exceptionTypes = o, method::getExceptionTypes);
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(getAnnotations().get(annotationClass));
    }

    public boolean isMethodAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return getMethodAnnotation(annotationClass) != null;
    }

    public <T extends Annotation> T getMethodAnnotation(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(getMethodAnnotations().get(annotationClass));
    }

    public boolean isClassAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return getClassAnnotation(annotationClass) != null;
    }

    public <T extends Annotation> T getClassAnnotation(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(getClassAnnotations().get(annotationClass));
    }

    private Map<Class<? extends Annotation>, Annotation> getAnnotations() {
        return cacheAndReturn(() -> annotations, o -> annotations = o, ()
                -> Stream.concat(Arrays.stream(method.getDeclaringClass().getAnnotations()),
                        Arrays.stream(method.getAnnotations()))
                .collect(LinkedHashMap::new, (m, o) -> m.put(o.annotationType(), o), Map::putAll));
    }

    private Map<Class<? extends Annotation>, Annotation> getMethodAnnotations() {
        return cacheAndReturn(() -> methodAnnotations, o -> methodAnnotations = o, ()
                -> Arrays.stream(method.getAnnotations())
                .collect(LinkedHashMap::new, (m, o) -> m.put(o.annotationType(), o), Map::putAll));
    }

    private Map<Class<? extends Annotation>, Annotation> getClassAnnotations() {
        return cacheAndReturn(() -> classAnnotations, o -> classAnnotations = o, ()
                -> Arrays.stream(method.getDeclaringClass().getAnnotations())
                .collect(LinkedHashMap::new, (m, o) -> m.put(o.annotationType(), o), Map::putAll));
    }

    private <T> T cacheAndReturn(Supplier<T> getter, Consumer<T> setter, Supplier<T> supplier) {
        T cache;
        if ((cache = getter.get()) == null) {
            synchronized (this) {
                if ((cache = getter.get()) == null) {
                    setter.accept((cache = supplier.get()));
                }
            }
        }
        return cache;
    }
}
