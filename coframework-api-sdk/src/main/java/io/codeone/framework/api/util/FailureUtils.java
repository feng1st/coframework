package io.codeone.framework.api.util;

import io.codeone.framework.api.exception.ApiError;
import lombok.experimental.UtilityClass;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * Utility for converting {@link ApiError} to failure responses.
 *
 * <p>Supports {@code FailureConverter} integration.
 */
@UtilityClass
public class FailureUtils {

    /**
     * Conversion service for failure responses.
     */
    public final GenericConversionService CONVERSION_SERVICE = new GenericConversionService();

    /**
     * Checks if {@link ApiError} can be converted to the given type.
     *
     * @param resultType the target type
     * @return true if conversion is supported, false otherwise
     */
    public boolean isSupported(Class<?> resultType) {
        if (resultType == Object.class) {
            return false;
        }
        return CONVERSION_SERVICE.canConvert(ApiError.class, resultType);
    }

    /**
     * Converts an {@link ApiError} to the specified type.
     *
     * @param apiError   the error to convert, may be null
     * @param resultType the target type
     * @param <T>        the result type
     * @return the converted result, or null if conversion fails
     */
    public <T> T toFailure(ApiError apiError, Class<T> resultType) {
        if (apiError == null) {
            return null;
        }
        if (CONVERSION_SERVICE.canConvert(ApiError.class, resultType)) {
            return CONVERSION_SERVICE.convert(apiError, resultType);
        }
        return null;
    }
}
