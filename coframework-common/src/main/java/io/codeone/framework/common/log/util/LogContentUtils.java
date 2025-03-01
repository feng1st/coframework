package io.codeone.framework.common.log.util;

import io.codeone.framework.common.json.util.JsonUtils;
import io.codeone.framework.common.util.TypeStringUtils;
import lombok.experimental.UtilityClass;
import org.springframework.util.ClassUtils;

import java.util.regex.Pattern;

/**
 * Utilities for sanitizing log content. Provides methods to convert arbitrary objects
 * to logging-safe keys and values that comply with structured logging requirements.
 */
@UtilityClass
public class LogContentUtils {

    /**
     * Pattern for detecting non-safe characters in log keys (alphanumerics, underscores,
     * dots, and hyphens are allowed)
     */
    private final Pattern UNSAFE_KEY_PATTERN = Pattern.compile("[^a-zA-Z0-9_.-]");

    /**
     * Converts an object to a log-safe key string. Sanitizes using the following
     * rules:
     * <ul>
     *   <li>Converts {@code null} to string "null"</li>
     *   <li>Replaces invalid characters with underscores</li>
     *   <li>Handles {@code toString()} failures with error placeholder</li>
     * </ul>
     *
     * @param key original key object
     * @return sanitized key string with unsafe characters replaced
     */
    public String toLogSafeKey(Object key) {
        if (key == null) {
            return "null";
        }
        try {
            return UNSAFE_KEY_PATTERN.matcher(key.toString()).replaceAll("_");
        } catch (Throwable e) {
            return String.format("(TO_STRING_ERROR: %s)", TypeStringUtils.toString(key.getClass()));
        }
    }

    /**
     * Converts an object to a log-safe value according to these rules:
     * <ul>
     *   <li>Null remains null</li>
     *   <li>Strings and primitives are returned unchanged</li>
     *   <li>Enums use their {@code toString()} representation</li>
     *   <li>Other objects use JSON serialization if available</li>
     *   <li>Fallback to {@code toString()} with error handling</li>
     * </ul>
     *
     * @param object original value to sanitize
     * @return safe logging representation, may be original value, JSON string,
     * or error placeholder
     */
    public Object toLogSafeValue(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return object;
        }
        if (ClassUtils.isPrimitiveOrWrapper(object.getClass())) {
            return object;
        }
        if (object instanceof Enum) {
            return toString(object);
        }
        if (JsonUtils.isLoaded()) {
            return toJsonString(object);
        }
        return toString(object);
    }

    /**
     * Attempts JSON serialization with error fallback
     *
     * @param object object to serialize
     * @return JSON string or error placeholder
     */
    private String toJsonString(Object object) {
        try {
            return JsonUtils.toJsonString(object);
        } catch (Throwable ignored) {
            return String.format("(TO_JSON_STRING_ERROR: %s)", TypeStringUtils.toString(object.getClass()));
        }
    }

    /**
     * Safely converts to string with error handling
     *
     * @param object object to stringify
     * @return string value or error placeholder
     */
    private String toString(Object object) {
        try {
            return String.valueOf(object);
        } catch (Throwable e) {
            return String.format("(TO_STRING_ERROR: %s)", TypeStringUtils.toString(object.getClass()));
        }
    }
}
