package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AnnotationMethodPlugger<A extends Annotation>
        implements MethodPlugger {

    @Override
    public List<Plugin<?>> getPlugins(Method method) {
        Class<A> annoType = getAnnotationType();
        A anno = method.getAnnotation(annoType);
        if (anno == null) {
            anno = method.getDeclaringClass().getAnnotation(annoType);
            if (anno == null) {
                return null;
            }
        }
        return getPlugins(method, anno);
    }

    protected abstract Class<A> getAnnotationType();

    protected List<Plugin<?>> getPlugins(Method method, A annotation) {
        return null;
    }
}
