package io.codeone.framework.util;

import io.codeone.framework.request.ApiParam;

import java.util.Collection;
import java.util.Map;

/**
 * ArgChecker provides a variety of util functions for checking parameters.
 *
 * @see ApiParam
 */
public final class ArgChecker {

    private ArgChecker() {
    }

    public static void check(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNull(Object value, String message) {
        if (value != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotEmpty(String value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotEmpty(Collection<?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotEmpty(Map<?, ?> value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkEmpty(String value, String message) {
        if (value != null && !value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkEmpty(Collection<?> value, String message) {
        if (value != null && !value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkEmpty(Map<?, ?> value, String message) {
        if (value != null && !value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkIn(Object value, Collection<?> expectedSet,
                               String message) {
        if (value == null || !expectedSet.contains(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotIn(Object value, Collection<?> unexpectedSet,
                                  String message) {
        if (value != null && unexpectedSet.contains(value)) {
            throw new IllegalArgumentException(message);
        }
    }
}
