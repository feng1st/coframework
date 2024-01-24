package io.codeone.framework.sdk.util;

import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.exception.CommonErrors;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

/**
 * Utilities to handle exceptions.
 */
@UtilityClass
public class ErrorUtils {

    /**
     * Returns the top-most custom exception ({@link ApiError}) or
     * {@code IllegalArgumentException} in the hierarchy of the
     * {@code Throwable} as a meaningful cause, which will be used in logging
     * and result wrapping. Returns the root cause of the {@code Throwable} if
     * there is no {@code ApiError} or {@code IllegalArgumentException} in the
     * hierarchy.
     *
     * @param t the caught exception
     * @return a more meaningful cause
     */
    public Throwable getCause(Throwable t) {
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
     * Interprets the code from a cause. Returns {@link ApiError#getCode()} if
     * it was an {@code ApiError}, returns {@code "INVALID_PARAM"} if it was a
     * {@code IllegalArgumentException}, otherwise returns the simple name of
     * its class.
     *
     * @param cause the exception being interpreted
     * @return the string code of the cause
     */
    public String getCode(Throwable cause) {
        if (cause instanceof ApiError) {
            return ((ApiError) cause).getCode();
        }
        if (cause instanceof IllegalArgumentException) {
            return CommonErrors.INVALID_PARAM.getCode();
        }
        return cause.getClass().getSimpleName();
    }
}
