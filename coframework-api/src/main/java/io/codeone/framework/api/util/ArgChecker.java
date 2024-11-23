package io.codeone.framework.api.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Map;

// FIXME: rename
@UtilityClass
public class ArgChecker {

    public void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkNotNull(Object arg, String message) {
        if (arg == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkNull(Object arg, String message) {
        if (arg != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkNotEmpty(String arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkNotEmpty(Collection<?> arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkNotEmpty(Map<?, ?> arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkEmpty(String arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkEmpty(Collection<?> arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkEmpty(Map<?, ?> arg, String message) {
        if (arg != null && !arg.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkIn(Object arg, Collection<?> set, String message) {
        if (arg == null || !set.contains(arg)) {
            throw new IllegalArgumentException(message);
        }
    }

    public void checkNotIn(Object arg, Collection<?> set, String message) {
        if (arg != null && set.contains(arg)) {
            throw new IllegalArgumentException(message);
        }
    }
}
