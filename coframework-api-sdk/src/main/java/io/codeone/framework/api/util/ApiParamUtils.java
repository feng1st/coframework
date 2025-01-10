package io.codeone.framework.api.util;

import io.codeone.framework.api.parameter.ApiParam;
import lombok.experimental.UtilityClass;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * Utility for {@link ApiParam} conversions.
 *
 * <p>Supports {@code ApiParamConverter} integration.
 */
@UtilityClass
public class ApiParamUtils {

    /**
     * Conversion service for {@link ApiParam}.
     */
    public final GenericConversionService CONVERSION_SERVICE = new GenericConversionService();

    /**
     * Converts an object to {@link ApiParam}, if possible.
     *
     * @param arg the object to convert, may be null
     * @return the {@link ApiParam}, or null if conversion fails
     */
    public ApiParam toApiParam(Object arg) {
        if (arg == null) {
            return null;
        }
        if (arg instanceof ApiParam) {
            return (ApiParam) arg;
        }
        if (CONVERSION_SERVICE.canConvert(arg.getClass(), ApiParam.class)) {
            ApiParam apiParam = CONVERSION_SERVICE.convert(arg, ApiParam.class);
            if (apiParam != null) {
                return apiParam;
            }
        }
        return null;
    }
}
