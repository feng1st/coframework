package io.codeone.framework.api.util;

import io.codeone.framework.api.exception.ApiError;
import lombok.experimental.UtilityClass;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for handling {@link ApiError}.
 *
 * <p>Provides methods to extract and convert exceptions to {@link ApiError},
 * and retrieve error codes for service operations.
 */
@UtilityClass
public class ApiErrorUtils {

    /**
     * Conversion service for converting exceptions to {@link ApiError}.
     */
    public final GenericConversionService CONVERSION_SERVICE = new GenericConversionService();

    /**
     * Extracts the underlying cause of the given throwable.
     *
     * @param throwable the throwable to analyze
     * @return the root cause throwable or null if the input is null
     */
    public ApiError getCause(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        Set<Throwable> checked = new HashSet<>();
        checked.add(throwable);
        while (true) {
            if (throwable instanceof ApiError) {
                return (ApiError) throwable;
            }
            if (CONVERSION_SERVICE.canConvert(throwable.getClass(), ApiError.class)) {
                ApiError apiError = CONVERSION_SERVICE.convert(throwable, ApiError.class);
                if (apiError != null) {
                    return apiError;
                }
            }
            Throwable cause = throwable.getCause();
            if (cause == null
                    || !checked.add(cause)) {
                break;
            }
            throwable = cause;
        }
        return ApiError.of(throwable.getClass().getSimpleName(), true, throwable.getMessage());
    }
}
