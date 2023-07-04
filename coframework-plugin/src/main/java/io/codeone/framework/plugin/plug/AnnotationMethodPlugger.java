package io.codeone.framework.plugin.plug;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AnnotationMethodPlugger<A extends Annotation>
        implements MethodPlugger {

    @Override
    public List<Plugging> getPluggingList(Method method) {
        A anno = getAnnotation(method);
        if (anno == null) {
            return null;
        }
        return getPluggingList(method, anno);
    }

    protected abstract Class<A> getAnnotationType();

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

    protected abstract List<Plugging> getPluggingList(Method method, A annotation);
}
