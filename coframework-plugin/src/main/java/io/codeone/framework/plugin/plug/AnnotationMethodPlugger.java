package io.codeone.framework.plugin.plug;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * A {@link MethodPlugger} which makes a specific annotation a plugin enabler.
 *
 * @param <A> type of the annotation
 */
public abstract class AnnotationMethodPlugger<A extends Annotation>
        implements MethodPlugger {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Plugging> getPluggingList(Method method) {
        A anno = getAnnotation(method);
        if (anno == null) {
            return null;
        }
        return getPluggingList(method, anno);
    }

    /**
     * Returns the class of plugin enabling annotation.
     *
     * @return the class of plugin enabling annotation
     */
    protected abstract Class<A> getAnnotationType();

    /**
     * Returns the plugin enabling annotation instance from the target method or
     * its declaring class. If both method and class have the same type of
     * annotation, returns the method's.
     *
     * @param method the target method
     * @return the plugin enabling annotation
     */
    protected A getAnnotation(Method method) {
        Class<A> annoType = getAnnotationType();
        A anno;
        if ((anno = method.getAnnotation(annoType)) != null) {
            return anno;
        }
        Class<?> clazz = method.getDeclaringClass();
        if ((anno = clazz.getAnnotation(annoType)) != null) {
            return anno;
        }
        return null;
    }

    /**
     * Returns the {@link Plugging} list which is determined by both the target
     * method and the specific plugin enabling annotation.
     *
     * @param method     the target method is being tested
     * @param annotation the specific plugin enabling annotation
     * @return list of {@code Plugging} which defines what plugins should be
     * plugged into the target method
     */
    protected abstract List<Plugging> getPluggingList(Method method, A annotation);
}
