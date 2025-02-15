package io.codeone.framework.common.log.util;

import io.codeone.framework.common.log.formatter.LogFormatter;
import io.codeone.framework.common.util.TypeStringUtils;
import lombok.experimental.UtilityClass;
import org.springframework.util.ClassUtils;

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
                return String.format("(REF: %s)", TypeStringUtils.toString(object.getClass()));
            }
            try {
                Map<Object, Object> map = new LinkedHashMap<>(((Map<?, ?>) object).size());
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
                    map.put(toLogSafeObj(entry.getKey(), visited), toLogSafeObj(entry.getValue(), visited));
                }
                return map;
            } catch (Exception e) {
                return String.format("(ITERATE_MAP_ERROR: %s)", TypeStringUtils.toString(object.getClass()));
            }
        }
        if (object instanceof Collection) {
            if (visited.put(object, object) != null) {
                return String.format("(REF: %s)", TypeStringUtils.toString(object.getClass()));
            }
            try {
                List<Object> list = new ArrayList<>(((Collection<?>) object).size());
                for (Object element : (Collection<?>) object) {
                    list.add(toLogSafeObj(element, visited));
                }
                return list;
            } catch (Exception e) {
                return String.format("(ITERATE_COLLECTION_ERROR: %s)", TypeStringUtils.toString(object.getClass()));
            }
        }
        if (ClassUtils.isPrimitiveArray(object.getClass())) {
            return String.format("[%s]", TypeStringUtils.toString(object.getClass().getComponentType()));
        }
        if (ClassUtils.isPrimitiveOrWrapper(object.getClass())) {
            return object;
        }
        if (ClassUtils.isPrimitiveWrapperArray(object.getClass())) {
            return Arrays.asList((Object[]) object);
        }
        if (object.getClass().isArray()) {
            if (visited.put(object, object) != null) {
                return String.format("(REF: [%s])", TypeStringUtils.toString(object.getClass().getComponentType()));
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
            return String.format("(TO_STRING_ERROR: %s)", TypeStringUtils.toString(object.getClass()));
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
