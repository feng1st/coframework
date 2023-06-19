package io.codeone.framework.util;

import io.codeone.framework.exception.ApiError;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.exception.SysError;

import java.util.HashSet;
import java.util.Set;

/**
 * Utilities to handle exceptions.
 */
public final class ErrorUtils {

    private ErrorUtils() {
    }

    /**
     * Returns the top-most user custom exception, or IllegalArgumentException,
     * as a reasonable cause. Or returns the root cause of the throwable.
     */
    public static Throwable getCause(Throwable t) {
        Set<Throwable> set = new HashSet<>();
        set.add(t);
        while (!(t instanceof ApiError
                || t instanceof IllegalArgumentException)) {
            Throwable cause = t.getCause();
            // Found the root cause.
            if (cause == null
                    // Or to break in case there is a circle.
                    || !set.add(cause)) {
                break;
            }
            t = cause;
        }
        return t;
    }

    /**
     * Returns whether this cause is NOT an error that the system admin should
     * pay close attension to.
     */
    public static boolean isWarnOnly(Throwable cause) {
        return cause instanceof ApiError
                && !(cause instanceof SysError);
    }

    /**
     * Interprets the code from a cause. Returns ApiError::getCode() if it was
     * an ApiError, returns "INVALID_PARAM" if it was a
     * IllegalArgumentException, otherwise returns the simple name of its
     * class.
     */
    public static String getCode(Throwable cause) {
        if (cause instanceof ApiError) {
            return ((ApiError) cause).getCode();
        }
        if (cause instanceof IllegalArgumentException) {
            return CommonErrors.INVALID_PARAM.getCode();
        }
        return cause.getClass().getSimpleName();
    }
}
