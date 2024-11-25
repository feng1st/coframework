package io.codeone.framework.plugin.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.aop.support.AopUtils;

@UtilityClass
public class ClassUtils {

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> Class<T> forName(String name, ClassLoader classLoader) {
        return (Class<T>) org.springframework.util.ClassUtils.forName(name, classLoader);
    }

    public Class<?> getTargetClass(Object obj) {
        if (AopUtils.isAopProxy(obj)) {
            return AopUtils.getTargetClass(obj);
        }
        return obj.getClass();
    }
}
