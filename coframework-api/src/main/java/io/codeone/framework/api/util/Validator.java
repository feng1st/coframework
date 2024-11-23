package io.codeone.framework.api.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class Validator {

    public void requireTrue(Boolean value, String message) {
        if (value == null || !value) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireFalse(Boolean value, String message) {
        if (value == null || value) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonTrue(Boolean value, String message) {
        if (value != null && value) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonFalse(Boolean value, String message) {
        if (value != null && !value) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNull(Object value, String message) {
        if (value != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonEmpty(String value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonEmpty(Collection<?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonEmpty(Map<?, ?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireEmpty(String value, String message) {
        if (value != null && !value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireEmpty(Collection<?> value, String message) {
        if (value != null && !value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireEmpty(Map<?, ?> value, String message) {
        if (value != null && !value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireEquals(Object value, Object expected, String message) {
        if (!Objects.equals(value, expected)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNotEquals(Object value, Object unexpected, String message) {
        if (Objects.equals(value, unexpected)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireIn(Object value, Collection<?> collection, String message) {
        if (value == null || !collection.contains(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNotIn(Object value, Collection<?> collection, String message) {
        if (value != null && collection.contains(value)) {
            throw new IllegalArgumentException(message);
        }
    }
}
