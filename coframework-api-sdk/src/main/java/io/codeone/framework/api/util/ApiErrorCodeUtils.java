package io.codeone.framework.api.util;

import io.codeone.framework.api.exception.ApiErrorCode;
import lombok.experimental.UtilityClass;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for handling {@link ApiErrorCode}.
 *
 * <p>Provides methods to extract and convert exceptions to {@link ApiErrorCode},
 * and retrieve error codes for service operations.
 */
@UtilityClass
public class ApiErrorCodeUtils {

    /**
     * Conversion service for converting exceptions to {@link ApiErrorCode}.
     */
    public final GenericConversionService CONVERSION_SERVICE = new GenericConversionService();

    /**
     * Extracts the underlying cause of the given throwable.
     *
     * @param throwable the throwable to analyze
     * @return the root cause throwable or null if the input is null
     */
    public Throwable getCause(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        Set<Throwable> checked = new HashSet<>();
        checked.add(throwable);
        while (true) {
            if (throwable instanceof ApiErrorCode
                    || CONVERSION_SERVICE.canConvert(throwable.getClass(), ApiErrorCode.class)) {
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
     * Converts the given throwable into an {@link ApiErrorCode} instance.
     *
     * @param throwable the throwable to convert
     * @return the corresponding {@code ApiErrorCode}, or null if the input is null
     */
    public ApiErrorCode toApiErrorCode(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        if (throwable instanceof ApiErrorCode) {
            return (ApiErrorCode) throwable;
        }
        if (CONVERSION_SERVICE.canConvert(throwable.getClass(), ApiErrorCode.class)) {
            ApiErrorCode apiErrorCode = CONVERSION_SERVICE.convert(throwable, ApiErrorCode.class);
            if (apiErrorCode != null) {
                return apiErrorCode;
            }
        }
        return ApiErrorCode.of(throwable.getClass().getSimpleName(), true);
    }
}
