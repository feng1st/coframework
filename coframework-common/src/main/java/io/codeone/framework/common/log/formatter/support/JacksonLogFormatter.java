package io.codeone.framework.common.log.formatter.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.codeone.framework.common.log.formatter.LogFormatter;
import lombok.SneakyThrows;

/**
 * Log formatter using Jackson for JSON conversion.
 *
 * <p>Formats log content as a JSON string using Jackson's {@link ObjectMapper}.
 */
public class JacksonLogFormatter implements LogFormatter {

    private final ObjectMapper objectMapper;

    public JacksonLogFormatter() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    @SneakyThrows
    public Object format(Object content) {
        return objectMapper.writeValueAsString(content);
    }
}