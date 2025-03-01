package io.codeone.framework.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;

/**
 * Utility class for working with Java classes and class loaders.
 */
@UtilityClass
public class ClassUtils {

    /**
     * Retrieves the simple name of a class, handling lambda-generated classes.
     *
     * @param clazz the class to inspect
     * @return the simple name of the class
     */
    public String getSimpleName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        int i = simpleName.lastIndexOf("$$Lambda");
        if (i != -1) {
            return simpleName.substring(0, i + 8);
        }
        return simpleName;
    }

    /**
     * Retrieves the simple name of a method.
     *
     * @param method the method to inspect
     * @return the simple name of the method
     */
    public String getSimpleName(Method method) {
        return getSimpleName(method.getDeclaringClass()) + "." + method.getName();
    }

    /**
     * Loads a class by name using the specified class loader.
     *
     * @param name        the fully qualified name of the class (must not be null)
     * @param classLoader the class loader to use for loading (may be null for default)
     * @param <T>         the expected class type
     * @return the loaded class (never null)
     * @throws RuntimeException if the class cannot be found
     */
    @SuppressWarnings("unchecked")
    public <T> Class<T> forName(String name, ClassLoader classLoader) {
        try {
            return (Class<T>) org.springframework.util.ClassUtils.forName(name, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
