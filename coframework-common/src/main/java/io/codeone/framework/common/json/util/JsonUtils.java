package io.codeone.framework.common.json.util;

import io.codeone.framework.common.json.mapper.JsonMapper;
import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * Utility class for JSON operations. Dynamically loads a {@link JsonMapper} implementation
 * from the classpath (falls back to null if unavailable).
 */
@UtilityClass
public class JsonUtils {

    private final JsonMapper mapper = loadMapper();

    /**
     * Indicates whether a JSON mapper implementation was successfully loaded.
     *
     * @return true if a valid mapper is available, false otherwise
     */
    public boolean isLoaded() {
        return mapper != null;
    }

    /**
     * Converts an object to JSON string using the loaded mapper.
     *
     * @param object the object to serialize (may be null)
     * @return JSON string representation
     * @throws NullPointerException if no mapper implementation is loaded
     * @throws RuntimeException     if serialization fails (implementation-specific)
     */
    public String toJsonString(Object object) {
        Objects.requireNonNull(mapper);
        return mapper.toJsonString(object);
    }

    /**
     * Attempts to load the Jackson-based mapper implementation via reflection.
     *
     * @return configured {@code JacksonJsonMapper} instance, or null if unavailable
     */
    private JsonMapper loadMapper() {
        try {
            Class<?> clazz = Class.forName("io.codeone.framework.common.json.mapper.support.JacksonJsonMapper");
            return (JsonMapper) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
