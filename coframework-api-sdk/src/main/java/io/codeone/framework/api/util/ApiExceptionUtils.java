package io.codeone.framework.api.util;

import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.exception.CommonCodes;
import lombok.experimental.UtilityClass;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for handling {@link ApiException} operations.
 *
 * <p>Provides methods to extract and convert exceptions to {@link ApiException},
 * and retrieve error codes for service operations.
 */
@UtilityClass
public class ApiExceptionUtils {

    /**
     * Conversion service for converting exceptions to {@link ApiException}.
     */
    public final GenericConversionService CONVERSION_SERVICE = new GenericConversionService();

    /**
     * Extracts the underlying cause of the given throwable.
     *
     * @param throwable the throwable to analyze
     * @return the root cause throwable or null if none is found
     */
    public Throwable getCause(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        Set<Throwable> checked = new HashSet<>();
        checked.add(throwable);
        while (true) {
            if (throwable instanceof ApiException
                    || CONVERSION_SERVICE.canConvert(throwable.getClass(), ApiException.class)
                    || throwable instanceof IllegalArgumentException) {
                break;
            }
            Throwable cause = throwable.getCause();
            if (cause == null
                    || !checked.add(cause)) {
                break;
            }
            throwable = cause;
        }
        return throwable;
    }

    /**
     * Retrieves the error code for the given throwable.
     *
     * @param cause the throwable to analyze
     * @return the corresponding error code
     */
    public String getCode(Throwable cause) {
        if (cause instanceof ApiException) {
            return ((ApiException) cause).getCode();
        }
        if (CONVERSION_SERVICE.canConvert(cause.getClass(), ApiException.class)) {
            ApiException apiException = CONVERSION_SERVICE.convert(cause, ApiException.class);
            if (apiException != null) {
                return apiException.getCode();
            }
        }
        if (cause instanceof IllegalArgumentException) {
            return CommonCodes.INVALID_ARGS;
        }
        return cause.getClass().getSimpleName();
    }
}
