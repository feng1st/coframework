package io.codeone.framework.common.log.formatter.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codeone.framework.common.log.formatter.LogFormatter;

/**
 * Log formatter using Jackson for JSON conversion.
 *
 * <p>Formats log content as a JSON string using Jackson's {@link ObjectMapper}.
 */
public class JacksonLogFormatter implements LogFormatter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object format(Object content) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (Exception ignored) {
            return content;
        }
    }
}