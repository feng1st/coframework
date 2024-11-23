package io.codeone.framework.api.util;

import io.codeone.framework.api.response.ApiResult;
import lombok.experimental.UtilityClass;
import org.springframework.core.convert.support.GenericConversionService;

@UtilityClass
public class ApiResultUtils {

    public final GenericConversionService CONVERSION_SERVICE = new GenericConversionService();

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
