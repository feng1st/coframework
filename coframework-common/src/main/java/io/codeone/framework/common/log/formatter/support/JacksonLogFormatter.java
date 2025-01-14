package io.codeone.framework.common.log.formatter.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.codeone.framework.common.log.formatter.LogFormatter;
import org.springframework.util.ClassUtils;

import java.util.*;

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
        objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public Object format(Object content) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (Exception ignored) {
            // FIXME
            try {
                return objectMapper.writeValueAsString(toSafeContent(content));
            } catch (Exception e) {
                return String.format("FORMAT_ERROR(%s)", e.getMessage());
            }
        }
    }

    private Object toSafeContent(Object content) {
        return toSafeContent(content, new IdentityHashMap<>());
    }

    private Object toSafeContent(Object content, IdentityHashMap<Object, Object> visited) {
        if (content == null) {
            return content;
        }
        if (ClassUtils.isPrimitiveOrWrapper(content.getClass())) {
            return content;
        }
        if (ClassUtils.isPrimitiveArray(content.getClass())) {
            return content;
        }
        if (content instanceof String) {
            return content;
        }
        if (content instanceof Map) {
            if (visited.put(content, content) != null) {
                return null;
            }
            Map<Object, Object> map = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) content).entrySet()) {
                map.put(toSafeContent(entry.getKey(), visited), toSafeContent(entry.getValue(), visited));
            }
            return map;
        }
        if (content instanceof Iterable) {
            if (visited.put(content, content) != null) {
                return null;
            }
            List<Object> list = new ArrayList<>();
            for (Object element : (Iterable<?>) content) {
                list.add(toSafeContent(element, visited));
            }
            return list;
        }
        if (content.getClass().isArray()) {
            if (visited.put(content, content) != null) {
                return null;
            }
            List<Object> list = new ArrayList<>();
            for (Object element : (Object[]) content) {
                list.add(toSafeContent(element, visited));
            }
            return list;
        }
        try {
            return content.toString();
        } catch (Throwable e) {
            return "(TO_STRING_ERROR)";
        }
    }
}