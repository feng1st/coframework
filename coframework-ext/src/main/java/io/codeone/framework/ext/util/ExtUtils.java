package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.annotation.Extensible;
import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing common operations for handling extensible interfaces
 * and {@link BizScenarioParam} types.
 */
@UtilityClass
public class ExtUtils {

    /**
     * Retrieves all interfaces implemented by the given class that are annotated
     * with {@code Ability} or {@code ExtensionPoint}.
     *
     * @param extClass the class to analyze
     * @return a list of extensible interfaces annotated with {@code Ability} or
     * {@code ExtensionPoint}
     */
    public List<Class<?>> getAllExtensibleInterfaces(Class<?> extClass) {
        return Arrays.stream(ClassUtils.getAllInterfacesForClass(extClass))
                .filter(o -> AnnotationUtils.findAnnotation(o, Extensible.class) != null)
                .collect(Collectors.toList());
    }

    /**
     * Determines whether the specified type implements {@link BizScenarioParam}.
     *
     * @param paramType the type to check
     * @return {@code true} if the type is assignable to {@link BizScenarioParam},
     * otherwise {@code false}
     */
    public boolean isBizScenarioParam(Class<?> paramType) {
        return BizScenarioParam.class.isAssignableFrom(paramType);
    }
}
