package io.codeone.framework.ext.util;

import io.codeone.framework.ext.Description;
import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utilities for extension model scanning.
 */
@UtilityClass
public class ScanModelUtils {

    /**
     * Return the unique key of a class.
     *
     * @param clazz the class of an extension model
     * @return the unique key of the class
     */
    public String getClassKey(Class<?> clazz) {
        return clazz.getName();
    }

    /**
     * Return the key of the declaring class of a method.
     *
     * @param method the method of an extension model
     * @return the key of the declaring class of a method
     */
    public String getClassKey(Method method) {
        return method.getDeclaringClass().getName();
    }

    /**
     * Returns the key of a method.
     *
     * @param method the method of an extension model
     * @return the key of a method
     */
    public String getMethodKey(Method method) {
        return method.getName()
                + Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(",", "(", ")"));
    }

    public String getName(Description description, Class<?> extensibleClass) {
        return description.name().isEmpty() ? extensibleClass.getSimpleName() : description.name();
    }

    public String getName(Description description, Method method) {
        return (description == null || description.name().isEmpty())
                ? method.getDeclaringClass().getSimpleName() + "." + method.getName() : description.name();
    }

    public String getDescription(Description description) {
        return description == null ? "" : description.description();
    }

    public int getOrder(Order order) {
        return order == null ? -1 : order.value();
    }
}
