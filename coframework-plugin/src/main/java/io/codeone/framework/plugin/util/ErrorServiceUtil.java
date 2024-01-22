package io.codeone.framework.plugin.util;

import io.codeone.framework.exception.ApiError;
import io.codeone.framework.exception.CommonErrors;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class ErrorServiceUtil {

    @Resource
    private ConversionServiceUtil conversionServiceUtil;

    public ApiError getCause(Throwable t) {
        if (t == null) {
            return null;
        }
        Set<Throwable> set = new HashSet<>();
        set.add(t);
        while (true) {
            ApiError apiError = conversionServiceUtil.convert(t, ApiError.class);
            if (apiError != null) {
                return apiError;
            }
            if (t instanceof IllegalArgumentException) {
                return ApiError.of(CommonErrors.INVALID_PARAM.getCode(), t.getMessage());
            }
            Throwable cause = t.getCause();
            if (cause == null
                    || !set.add(cause)) {
                break;
            }
            t = cause;
        }
        return ApiError.of(t.getClass().getSimpleName(), t.getMessage());
    }
}
