package io.codeone.framework.api.util;

import io.codeone.framework.api.response.ApiResult;
import lombok.experimental.UtilityClass;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * Utility class for handling {@link ApiResult} operations.
 *
 * <p>Provides methods to convert objects to {@link ApiResult} for service responses.
 */
@UtilityClass
public class ApiResultUtils {

    /**
     * Conversion service for converting objects to {@link ApiResult}.
     */
    public final GenericConversionService CONVERSION_SERVICE = new GenericConversionService();

    /**
     * Converts an object to an {@link ApiResult}, if possible.
     *
     * @param result the object to convert
     * @return the converted {@link ApiResult}, or null if the conversion is not
     * possible
     */
    public ApiResult<?> toApiResult(Object result) {
        if (result == null) {
            return null;
        }
        if (result instanceof ApiResult) {
            return (ApiResult<?>) result;
        }
        if (CONVERSION_SERVICE.canConvert(result.getClass(), ApiResult.class)) {
            ApiResult<?> apiResult = CONVERSION_SERVICE.convert(result, ApiResult.class);
            if (apiResult != null) {
                return apiResult;
            }
        }
        return null;
    }
}
