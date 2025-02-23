package io.codeone.framework.common.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;

/**
 * A utility class for formatting classes and methods into their string representations.
 *
 * <p>This class provides static methods to convert {@link Class} and {@link Method}
 * objects into strings suitable for logging, exception messages, and other user-facing
 * contexts.
 */
@UtilityClass
public class TypeStringUtils {

    /**
     * Returns the fully qualified type name of the given class.
     *
     * @param clazz the class to format (may be {@code null})
     * @return the fully qualified name of the class in standard Java syntax (e.g.,
     * "java.util.List"), or {@code null} if the input is {@code null}
     */
    public String toString(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return clazz.getTypeName();
    }

    /**
     * Formats a method signature with its parameter types.
     *
     * @param method the method to format (may be {@code null})
     * @return a string representing the method in the format {@code ClassName.methodName(ParamType1,
     * ParamType2)}, or {@code null} if the input is {@code null}
     */
    public String toString(Method method) {
        if (method == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(method.getDeclaringClass().getTypeName())
                .append(".")
                .append(method.getName())
                .append("(");
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(method.getParameterTypes()[i].getSimpleName());
        }
        sb.append(")");
        return sb.toString();
    }
}
