package io.codeone.framework.util;

import io.codeone.framework.request.ApiParam;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;

/**
 * ArgChecker provides essential util functions for checking parameters.
 *
 * @see ApiParam
 */
@UtilityClass
public class ArgChecker {

    /**
     * Checks if the given condition is true, throws IllegalArgumentException
     * otherwise.
     */
    public void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is not null, otherwise throws
     * IllegalArgumentException.
     */
    public void checkNotNull(Object arg, String message) {
        if (arg == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is null, otherwise throws
     * IllegalArgumentException.
     */
    public void checkNull(Object arg, String message) {
        if (arg != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is not null or empty, otherwise throws
     * IllegalArgumentException.
     */
    public void checkNotEmpty(String arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is not null or empty, otherwise throws
     * IllegalArgumentException.
     */
    public void checkNotEmpty(Collection<?> arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is not null or empty, otherwise throws
     * IllegalArgumentException.
     */
    public void checkNotEmpty(Map<?, ?> arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is null or empty, otherwise throws
     * IllegalArgumentException.
     */
    public void checkEmpty(String arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is null or empty, otherwise throws
     * IllegalArgumentException.
     */
    public void checkEmpty(Collection<?> arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is null or empty, otherwise throws
     * IllegalArgumentException.
     */
    public void checkEmpty(Map<?, ?> arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is not null and in the specific set, otherwise
     * throws IllegalArgumentException.
     */
    public void checkIn(Object arg, Collection<?> set, String message) {
        if (arg == null || !set.contains(arg)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns true if the arg is null or not in the specific set, otherwise
     * throws IllegalArgumentException.
     */
    public void checkNotIn(Object arg, Collection<?> set, String message) {
        if (arg != null && set.contains(arg)) {
            throw new IllegalArgumentException(message);
        }
    }
}
