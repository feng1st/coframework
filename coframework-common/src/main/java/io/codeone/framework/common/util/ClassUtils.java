package io.codeone.framework.common.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.aop.support.AopUtils;

/**
 * Utility class for working with Java classes and class loaders.
 */
@UtilityClass
public class ClassUtils {

    /**
     * Loads a class by name using the specified class loader.
     *
     * @param name        the fully qualified name of the class
     * @param classLoader the class loader to use for loading
     * @param <T>         the expected type of the class
     * @return the loaded class
     * @throws ClassNotFoundException if the class cannot be found
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> Class<T> forName(String name, ClassLoader classLoader) {
        return (Class<T>) org.springframework.util.ClassUtils.forName(name, classLoader);
    }

    /**
     * Retrieves the target class of an object, unwrapping proxies if necessary.
     *
     * @param obj the object to inspect
     * @return the target class of the object
     */
    public Class<?> getTargetClass(Object obj) {
        if (AopUtils.isAopProxy(obj)) {
            return AopUtils.getTargetClass(obj);
        }
        return obj.getClass();
    }
}
