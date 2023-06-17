package io.codeone.framework.util;

import io.codeone.framework.exception.ApiError;
import io.codeone.framework.exception.CommonErrors;

import java.util.HashSet;
import java.util.Set;

public final class ErrorUtils {

    private ErrorUtils() {
    }

    /**
     * Return the top-most user custom exception, or invalid param exception.
     */
    public static Throwable getCause(Throwable t) {
        Set<Throwable> set = new HashSet<>();
        set.add(t);
        while (!(t instanceof ApiError
                || t instanceof IllegalArgumentException)) {
            Throwable cause = t.getCause();
            if (cause == null || !set.add(cause)) {
                break;
            }
            t = cause;
        }
        return t;
    }

    public static String getCode(Throwable t) {
        if (t instanceof ApiError) {
            return ((ApiError) t).getCode();
        }
        if (t instanceof IllegalArgumentException) {
            return CommonErrors.INVALID_PARAM.getCode();
        }
        return t.getClass().getSimpleName();
    }
}
