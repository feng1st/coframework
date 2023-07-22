package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.Extensible;
import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ExtUtils {

    public List<Class<?>> getAllExtensibleClasses(Class<?> extClass) {
        return Arrays.stream(ClassUtils.getAllInterfacesForClass(extClass))
                .filter(o -> AnnotationUtils.findAnnotation(o, Extensible.class) != null)
                .collect(Collectors.toList());
    }

    public boolean isBizScenarioParam(Class<?> paramType) {
        return BizScenarioParam.class.isAssignableFrom(paramType);
    }
}
