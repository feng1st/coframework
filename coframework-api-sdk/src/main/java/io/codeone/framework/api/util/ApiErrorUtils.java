package io.codeone.framework.api.util;

import io.codeone.framework.api.exception.ApiError;
import lombok.experimental.UtilityClass;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility for extracting and converting exceptions to {@link ApiError}.
 *
 * <p>Supports {@code ApiErrorConverter} integration.
 */
@UtilityClass
public class ApiErrorUtils {

    /**
     * Conversion service for {@link ApiError}.
     */
    public final GenericConversionService CONVERSION_SERVICE = new GenericConversionService();

    /**
     * Converts a throwable to its root cause {@link ApiError}.
     *
     * @param throwable the throwable to analyze, may be null
     * @return the {@link ApiError}, or a default instance if conversion fails
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
