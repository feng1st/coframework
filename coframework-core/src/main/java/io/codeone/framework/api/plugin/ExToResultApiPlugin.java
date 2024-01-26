package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiConstants;
import io.codeone.framework.api.convert.ApiConversionService;
import io.codeone.framework.api.convert.ApiErrorConversionService;
import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.TargetMethod;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Optional;

@Plug(value = Stages.POST_RESULT_INTERCEPTING, group = ApiConstants.PLUGIN_GROUP)
public class ExToResultApiPlugin implements Plugin {

    @Resource
    private ApiConversionService apiConversionService;

    @Resource
    private ApiErrorConversionService apiErrorConversionService;

    @Override
    public Object afterThrowing(TargetMethod targetMethod, Object[] args, Throwable error)
            throws Throwable {
        return exToResult(targetMethod, error);
    }

    private Object exToResult(TargetMethod targetMethod, Throwable t)
            throws Throwable {
        Class<?> returnType = targetMethod.getReturnType();
        if (!apiConversionService.canConvert(ApiError.class, returnType)) {
            throw t;
        }
        ApiError cause = apiErrorConversionService.getCause(t);
        ApiError apiError = buildApiError(targetMethod, cause);
        Object result = apiConversionService.convert(apiError, returnType);
        if (result != null) {
            return result;
        }
        throw t;
    }

    private ApiError buildApiError(TargetMethod targetMethod, ApiError cause) {
        return Optional.ofNullable(targetMethod.getAnnotation(API.class))
                .map(API::errorMessage)
                .filter(StringUtils::hasText)
                .map(o -> ApiError.of(cause.getCode(), o))
                .orElse(cause);
    }
}
