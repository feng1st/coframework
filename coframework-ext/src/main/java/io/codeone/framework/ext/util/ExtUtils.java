package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.Extensible;
import io.codeone.framework.ext.Extension;
import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Some common utilities for Extension.
 */
@UtilityClass
public class ExtUtils {

    /**
     * Returns all Extensible interfaces of the given Extension class. The
     * Extension class should be annotated by {@link Extension} and the
     * Extensible interface should be annotated by {@link Extensible}.
     *
     * @param extClass the Extension class to be queried
     * @return all Extensible interfaces of the given Extension class
     */
    public List<Class<?>> getAllExtensibleClasses(Class<?> extClass) {
        return Arrays.stream(ClassUtils.getAllInterfacesForClass(extClass))
                .filter(o -> AnnotationUtils.findAnnotation(o, Extensible.class) != null)
                .collect(Collectors.toList());
    }

    /**
     * Returns {@code true} if the class of the specified parameter is a
     * subclass of {@link BizScenarioParam}, otherwise {@code false}.
     *
     * @param paramType the parameter type
     * @return {@code true} if the specified class is a subclass of
     * {@link BizScenarioParam}, otherwise {@code false}
     */
    public boolean isBizScenarioParam(Class<?> paramType) {
        return BizScenarioParam.class.isAssignableFrom(paramType);
    }
}
