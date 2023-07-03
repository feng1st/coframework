package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AnnotationMethodPlugger<A extends Annotation>
        implements MethodPlugger {

    @Override
    public List<Plugin> getPlugins(Method method) {
        A anno = getAnnotation(method);
        if (anno == null) {
            return null;
        }
        return getPlugins(method, anno);
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

    protected List<Plugin> getPlugins(Method method, A annotation) {
        return null;
    }
}
