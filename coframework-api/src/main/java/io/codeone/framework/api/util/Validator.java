package io.codeone.framework.api.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for validating method arguments.
 */
@UtilityClass
public class Validator {

    /**
     * Ensures the given condition is true.
     *
     * @param value   the condition to check
     * @param message the exception message if the condition is false
     * @throws IllegalArgumentException if the condition is false
     */
    public void requireTrue(Boolean value, String message) {
        if (value == null || !value) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given condition is false.
     *
     * @param value   the condition to check
     * @param message the exception message if the condition is true
     * @throws IllegalArgumentException if the condition is true
     */
    public void requireFalse(Boolean value, String message) {
        if (value == null || value) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given value is not true (i.e. false or null).
     *
     * @param value   the value to check
     * @param message the exception message if the value is true
     * @throws IllegalArgumentException if the value is true
     */
    public void requireNonTrue(Boolean value, String message) {
        if (value != null && value) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given value is not false (i.e. true or null).
     *
     * @param value   the value to check
     * @param message the exception message if the value is false
     * @throws IllegalArgumentException if the value is false
     */
    public void requireNonFalse(Boolean value, String message) {
        if (value != null && !value) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given value is not null.
     *
     * @param value   the value to check
     * @param message the exception message if the value is null
     * @throws IllegalArgumentException if the value is null
     */
    public void requireNonNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given value is null.
     *
     * @param value   the value to check
     * @param message the exception message if the value is not null
     * @throws IllegalArgumentException if the value is not null
     */
    public void requireNull(Object value, String message) {
        if (value != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given string is not empty.
     *
     * @param value   the string to check
     * @param message the exception message if the string is empty
     * @throws IllegalArgumentException if the string is null or empty
     */
    public void requireNonEmpty(String value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given collection is not empty.
     *
     * @param value   the collection to check
     * @param message the exception message if the collection is empty
     * @throws IllegalArgumentException if the collection is null or empty
     */
    public void requireNonEmpty(Collection<?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given map is not empty.
     *
     * @param value   the map to check
     * @param message the exception message if the map is empty
     * @throws IllegalArgumentException if the map is null or empty
     */
    public void requireNonEmpty(Map<?, ?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given string is empty.
     *
     * @param value   the string to check
     * @param message the exception message if the string is not empty
     * @throws IllegalArgumentException if the string is not empty
     */
    public void requireEmpty(String value, String message) {
        if (value != null && !value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given collection is empty.
     *
     * @param value   the collection to check
     * @param message the exception message if the collection is not empty
     * @throws IllegalArgumentException if the collection is not empty
     */
    public void requireEmpty(Collection<?> value, String message) {
        if (value != null && !value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given map is empty.
     *
     * @param value   the map to check
     * @param message the exception message if the map is not empty
     * @throws IllegalArgumentException if the map is not empty
     */
    public void requireEmpty(Map<?, ?> value, String message) {
        if (value != null && !value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given value is equal to the expected value.
     *
     * @param value    the value to check
     * @param expected the expected value
     * @param message  the exception message if the values are not equal
     * @throws IllegalArgumentException if the values are not equal
     */
    public void requireEquals(Object value, Object expected, String message) {
        if (!Objects.equals(value, expected)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given value is not equal to the unexpected value.
     *
     * @param value      the value to check
     * @param unexpected the unexpected value
     * @param message    the exception message if the values are equal
     * @throws IllegalArgumentException if the values are equal
     */
    public void requireNotEquals(Object value, Object unexpected, String message) {
        if (Objects.equals(value, unexpected)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given value is present in the specified collection.
     *
     * @param value      the value to check
     * @param collection the collection to check against
     * @param message    the exception message if the value is not in the collection
     * @throws IllegalArgumentException if the value is not in the collection
     */
    public void requireIn(Object value, Collection<?> collection, String message) {
        if (value == null || !collection.contains(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the given value is not present in the specified collection.
     *
     * @param value      the value to check
     * @param collection the collection to check against
     * @param message    the exception message if the value is in the collection
     * @throws IllegalArgumentException if the value is in the collection
     */
    public void requireNotIn(Object value, Collection<?> collection, String message) {
        if (value != null && collection.contains(value)) {
            throw new IllegalArgumentException(message);
        }
    }
}
