package io.codeone.framework.log.formatter.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codeone.framework.log.formatter.LogFormatter;

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