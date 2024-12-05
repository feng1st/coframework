package io.codeone.framework.common.log.util;

import io.codeone.framework.common.log.formatter.LogFormatter;
import lombok.experimental.UtilityClass;

/**
 * Utility class for log formatting.
 *
 * <p>Provides methods for formatting log content, with support for JSON formatting
 * using Jackson.
 */
@UtilityClass
public class LogUtils {

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
                return JACKSON_LOG_FORMATTER.format(content);
            }
        }
        return content;
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
