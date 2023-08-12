package io.codeone.framework.util;

import io.codeone.framework.request.ApiParam;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;

/**
 * ArgChecker provides essential util functions for parameters validation.
 *
 * @see ApiParam
 */
@UtilityClass
public class ArgChecker {

    /**
     * Checks if the given condition is {@code true}, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param condition the condition is being checked, and it is expected to be
     *                  {@code true}
     * @param message   the error message if the checking is failed
     */
    public void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the given arg is not null, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be
     *                non-null
     * @param message the error message if the checking is failed
     */
    public void checkNotNull(Object arg, String message) {
        if (arg == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the given arg is null, throws {@code IllegalArgumentException}
     * otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be null
     * @param message the error message if the checking is failed
     */
    public void checkNull(Object arg, String message) {
        if (arg != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the given string is not empty, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be not
     *                empty
     * @param message the error message if the checking is failed
     */
    public void checkNotEmpty(String arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the given collection is not empty, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be not
     *                empty
     * @param message the error message if the checking is failed
     */
    public void checkNotEmpty(Collection<?> arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the given map is not empty, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be not
     *                empty
     * @param message the error message if the checking is failed
     */
    public void checkNotEmpty(Map<?, ?> arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the given string is null or empty, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be null
     *                or empty
     * @param message the error message if the checking is failed
     */
    public void checkEmpty(String arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the given collection is null or empty, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be null
     *                or empty
     * @param message the error message if the checking is failed
     */
    public void checkEmpty(Collection<?> arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the given map is null or empty, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be null
     *                or empty
     * @param message the error message if the checking is failed
     */
    public void checkEmpty(Map<?, ?> arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the arg is not null and in the given set, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be
     *                non-null and is in the given set
     * @param set     contains all permitted values of the checked argument
     * @param message the error message if the checking is failed
     */
    public void checkIn(Object arg, Collection<?> set, String message) {
        if (arg == null || !set.contains(arg)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if the arg is null or is not in the given set, throws
     * {@code IllegalArgumentException} otherwise.
     *
     * @param arg     the argument is being checked, and it is expected to be
     *                null or is not in the given set
     * @param set     contains all forbidden values of the checked argument
     * @param message the error message if the checking is failed
     */
    public void checkNotIn(Object arg, Collection<?> set, String message) {
        if (arg != null && set.contains(arg)) {
            throw new IllegalArgumentException(message);
        }
    }
}
