package io.codeone.framework.logging.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codeone.framework.api.formatter.LogFormatter;
import org.springframework.stereotype.Component;

@Component
public class JsonLogFormatter implements LogFormatter {

    @Override
    public Object format(Object content) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(content);
        } catch (Exception e) {
            return content;
        }
    }
}
