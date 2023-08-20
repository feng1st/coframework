package io.codeone.framework.ext.util;

import lombok.experimental.UtilityClass;
import org.springframework.aop.support.AopUtils;

/**
 * Some common utilities for classes.
 */
@UtilityClass
public class ClassUtils {

    /**
     * Returns the class of the specified name and wraps any checked exception
     * to runtime exception.
     *
     * @param className the name of the class
     * @return the class of the specified name
     */
    public Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Returns the undelegated class of the given object.
     *
     * @param obj the object to be queried
     * @return the undelegated class of the object
     */
    public Class<?> getTargetClass(Object obj) {
        if (AopUtils.isAopProxy(obj)) {
            return AopUtils.getTargetClass(obj);
        }
        return obj.getClass();
    }
}
