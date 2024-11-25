package io.codeone.framework.log.util;

import io.codeone.framework.log.formatter.LogFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogUtils {

    public boolean logAsJson = true;

    private final LogFormatter JACKSON_LOG_FORMATTER = initJacksonLogFormatter();

    public Object format(Object content) {
        if (logAsJson) {
            if (JACKSON_LOG_FORMATTER != null) {
                return JACKSON_LOG_FORMATTER.format(content);
            }
        }
        return content;
    }

    private LogFormatter initJacksonLogFormatter() {
        try {
            Class.forName("com.fasterxml.jackson.databind.ObjectMapper");

            @SuppressWarnings("unchecked")
            Class<? extends LogFormatter> clazz = (Class<? extends LogFormatter>) Class.forName(
                    "io.codeone.framework.log.formatter.support.JacksonLogFormatter");
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
