package io.codeone.framework.api.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;

@UtilityClass
public class ArgValidator {

    public void requireTrue(Boolean arg, String message) {
        if (arg == null || !arg) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireFalse(Boolean arg, String message) {
        if (arg == null || arg) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonNull(Object arg, String message) {
        if (arg == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNull(Object arg, String message) {
        if (arg != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonEmpty(String arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonEmpty(Collection<?> arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNonEmpty(Map<?, ?> arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireEmpty(String arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireEmpty(Collection<?> arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireEmpty(Map<?, ?> arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireIn(Object arg, Collection<?> set, String message) {
        if (arg == null || !set.contains(arg)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void requireNotIn(Object arg, Collection<?> set, String message) {
        if (arg != null && set.contains(arg)) {
            throw new IllegalArgumentException(message);
        }
    }
}
