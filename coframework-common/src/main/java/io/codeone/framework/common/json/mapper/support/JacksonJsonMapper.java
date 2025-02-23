package io.codeone.framework.common.json.mapper.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.codeone.framework.common.json.mapper.JsonMapper;
import lombok.SneakyThrows;

/**
 * Jackson library implementation of {@link JsonMapper}. Configures serialization
 * rules:
 * <ul>
 *   <li>Disables failures for empty beans, self-references, and unwrapped types
 *   <li>Serializes self-references as null values
 *   <li>Writes dates as ISO-8601 strings instead of timestamps
 * </ul>
 */
public class JacksonJsonMapper implements JsonMapper {

    private final ObjectMapper objectMapper;

    /**
     * Creates a mapper with preconfigured serialization rules for lenient JSON
     * handling.
     */
    public JacksonJsonMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);
    }

    /**
     * {@inheritDoc}
     *
     * @throws RuntimeException if serialization fails (wraps checked exceptions)
     */
    @Override
    @SneakyThrows
    public String toJsonString(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
