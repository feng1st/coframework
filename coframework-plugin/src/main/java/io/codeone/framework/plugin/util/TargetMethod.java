package io.codeone.framework.plugin.util;

import lombok.Getter;

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
 * A wrap of {@code java.lang.reflect.Method}, and its commonly used properties.
 * All properties provided by this class are lazily instantiated and globally
 * cached.
 */
public class TargetMethod {

    private static final Map<Method, TargetMethod> CACHE = new ConcurrentHashMap<>();

    @Getter
    private final Method method;

    private Class<?>[] parameterTypes;

    private String[] parameterNames;

    private Class<?>[] exceptionTypes;

    private Map<Class<? extends Annotation>, Annotation> annotations;

    private Map<Class<? extends Annotation>, Annotation> methodAnnotations;

    private Map<Class<? extends Annotation>, Annotation> classAnnotations;

    /**
     * Returns a cached {@code TargetMethod}, or constructs one from the
     * {@code method}.
     *
     * @param method the source {@code Method}
     * @return a cached or new created {@code TargetMethod}
     */
    public static TargetMethod of(Method method) {
        return CACHE.computeIfAbsent(method, TargetMethod::new);
    }

    private TargetMethod(Method method) {
        this.method = method;
    }

    /**
     * Returns the declaring class of the wrapped method.
     *
     * @return the declaring class of the wrapped method
     */
    public Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }

    /**
     * Returns the types of its parameters in their declaration order. Returns
     * an array of length 0 if the wrapped method takes no parameter.
     *
     * @return the types of its parameters
     */
    public Class<?>[] getParameterTypes() {
        return cacheAndReturn(() -> parameterTypes, o -> parameterTypes = o, method::getParameterTypes);
    }

    /**
     * Returns the names of its parameters in their declaration order. Returns
     * an array of length 0 if the wrapped method takes no parameter.
     *
     * @return the names of its parameters
     */
    public String[] getParameterNames() {
        return cacheAndReturn(() -> parameterNames, o -> parameterNames = o, () ->
                Arrays.stream(method.getParameters()).map(Parameter::getName).toArray(String[]::new));
    }

    /**
     * Returns the return type of the wrapped method.
     *
     * @return the return type of the wrapped method
     */
    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    /**
     * Returns the types of exceptions declared by the wrapped method. Returns
     * an array of length 0 if the method declares no exceptions in its
     * {@code throws} clause.
     *
     * @return the exception types declared as being thrown by the wrapped
     * method
     */
    public Class<?>[] getExceptionTypes() {
        return cacheAndReturn(() -> exceptionTypes, o -> exceptionTypes = o, method::getExceptionTypes);
    }

    /**
     * Returns {@code true} if the specified annotation class is present on the
     * wrapped method, or its declaring class, otherwise {@code false}.
     *
     * @param annotationClass the annotation class is to be tested
     * @return {@code true} if the specified annotation class is present on the
     * wrapped method, or its declaring class, otherwise {@code false}
     */
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }

    /**
     * Returns the annotation instance of the given type on the wrapped method
     * or its declaring class. If both the method and the class have this type
     * of annotation, returns the one on method.
     *
     * @param annotationClass the class of annotation is to be acquired
     * @param <T>             type of the annotation
     * @return the annotation instance of the given type on the wrapped method
     * or its declaring class, method's comes first.
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(getAnnotations().get(annotationClass));
    }

    /**
     * Returns {@code true} if the specified annotation class is present on the
     * wrapped method, otherwise {@code false}.
     *
     * @param annotationClass the annotation class is to be tested
     * @return {@code true} if the specified annotation class is present on the
     * wrapped method, otherwise {@code false}
     */
    public boolean isMethodAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return getMethodAnnotation(annotationClass) != null;
    }

    /**
     * Returns the annotation instance of the given type on the wrapped method.
     *
     * @param annotationClass the class of annotation is to be acquired
     * @param <T>             type of the annotation
     * @return the annotation instance of the given type on the wrapped method
     */
    public <T extends Annotation> T getMethodAnnotation(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(getMethodAnnotations().get(annotationClass));
    }

    /**
     * Returns {@code true} if the specified annotation class is present on the
     * declaring class of the wrapped method, otherwise {@code false}.
     *
     * @param annotationClass the annotation class is to be tested
     * @return {@code true} if the specified annotation class is present on the
     * declaring class of the wrapped method, otherwise {@code false}
     */
    public boolean isClassAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return getClassAnnotation(annotationClass) != null;
    }

    /**
     * Returns the annotation instance of the given type on the declaring class
     * of the wrapped method.
     *
     * @param annotationClass the class of annotation is to be acquired
     * @param <T>             type of the annotation
     * @return the annotation instance of the given type on the declaring class
     * of the wrapped method.
     */
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
