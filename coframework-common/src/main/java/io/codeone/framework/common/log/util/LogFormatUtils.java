package io.codeone.framework.common.log.util;

import io.codeone.framework.common.log.formatter.LogFormatter;
import lombok.experimental.UtilityClass;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Utility class for log formatting.
 *
 * <p>Provides methods for formatting log content, with support for JSON formatting
 * using Jackson.
 */
@UtilityClass
public class LogFormatUtils {

    /**
     * Flag to determine if logging should be done in JSON format.
     */
    public boolean logAsJson = true;

    private final LogFormatter JACKSON_LOG_FORMATTER = initJacksonLogFormatter();

    /**
     * Retrieves the simple name of a class, handling lambda-generated classes.
     *
     * @param clazz the class to inspect
     * @return the simple name of the class
     */
    public String getSimpleName(Class<?> clazz) {
        return formatLambda(clazz.getSimpleName());
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
     * Return an informative string for a method.
     *
     * @param method the method to inspect
     * @return an informative string for the method
     */
    public String getTypeName(Method method) {
        StringBuilder builder = new StringBuilder();
        builder.append(formatLambda(method.getDeclaringClass().getTypeName()))
                .append(".")
                .append(method.getName())
                .append("(");
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(method.getParameterTypes()[i].getSimpleName());
        }
        builder.append(")");
        return builder.toString();
    }

    /**
     * Formats the given content for logging.
     *
     * @param content the content to format
     * @return the formatted log content
     */
    public Object format(Object content) {
        if (logAsJson) {
            if (JACKSON_LOG_FORMATTER != null) {
                try {
                    return JACKSON_LOG_FORMATTER.format(content);
                } catch (Throwable ignored) {
                    return JACKSON_LOG_FORMATTER.format(toLogSafeObj(content));
                }
            }
        }
        try {
            return content.toString();
        } catch (Throwable ignored) {
            return toLogSafeObj(content);
        }
    }

    private String formatLambda(String className) {
        int i = className.lastIndexOf("$$Lambda");
        if (i != -1) {
            return className.substring(0, i + 8);
        }
        return className;
    }

    /**
     * Safely converts an object to a loggable representation to avoid issues such
     * as cyclic references or exceptions during conversion.
     *
     * @param object the object to convert
     * @return a log-safe representation of the object
     */
    private Object toLogSafeObj(Object object) {
        return toLogSafeObj(object, new IdentityHashMap<>());
    }

    private Object toLogSafeObj(Object object, Map<Object, Object> visited) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return object;
        }
        if (object instanceof Map) {
            if (visited.put(object, object) != null) {
                return String.format("(REF: %s)", object.getClass().getName());
            }
            try {
                Map<Object, Object> map = new LinkedHashMap<>(((Map<?, ?>) object).size());
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
                    map.put(toLogSafeObj(entry.getKey(), visited), toLogSafeObj(entry.getValue(), visited));
                }
                return map;
            } catch (Exception e) {
                return String.format("(ITERATE_MAP_ERROR: %s)", object.getClass().getName());
            }
        }
        if (object instanceof Collection) {
            if (visited.put(object, object) != null) {
                return String.format("(REF: %s)", object.getClass().getName());
            }
            try {
                List<Object> list = new ArrayList<>(((Collection<?>) object).size());
                for (Object element : (Collection<?>) object) {
                    list.add(toLogSafeObj(element, visited));
                }
                return list;
            } catch (Exception e) {
                return String.format("(ITERATE_COLLECTION_ERROR: %s)", object.getClass().getName());
            }
        }
        if (ClassUtils.isPrimitiveArray(object.getClass())) {
            return String.format("[%s]", object.getClass().getComponentType().getName());
        }
        if (ClassUtils.isPrimitiveOrWrapper(object.getClass())) {
            return object;
        }
        if (ClassUtils.isPrimitiveWrapperArray(object.getClass())) {
            return Arrays.asList((Object[]) object);
        }
        if (object.getClass().isArray()) {
            if (visited.put(object, object) != null) {
                return String.format("(REF: [%s])", object.getClass().getComponentType().getName());
            }
            List<Object> list = new ArrayList<>(((Object[]) object).length);
            for (Object element : (Object[]) object) {
                list.add(toLogSafeObj(element, visited));
            }
            return list;
        }
        try {
            return object.toString();
        } catch (Throwable e) {
            return String.format("(TO_STRING_ERROR: %s)", object.getClass().getName());
        }
    }

    /**
     * Initializes the Jackson log formatter if Jackson is available on the classpath.
     *
     * @return an instance of {@code JacksonLogFormatter}, or null if Jackson is
     * not available
     */
    private LogFormatter initJacksonLogFormatter() {
        try {
            Class.forName("com.fasterxml.jackson.databind.ObjectMapper");

            @SuppressWarnings("unchecked")
            Class<? extends LogFormatter> clazz = (Class<? extends LogFormatter>) Class.forName(
                    "io.codeone.framework.common.log.formatter.support.JacksonLogFormatter");
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
