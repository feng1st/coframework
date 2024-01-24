package io.codeone.framework.api.plugin;

import io.codeone.framework.api.ApiConstants;
import io.codeone.framework.api.CustomErrorMessage;
import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.TargetMethod;
import io.codeone.framework.sdk.util.ApiConversionService;
import io.codeone.framework.sdk.util.ApiErrorConversionService;
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
        if (!apiConversionService.canConvert(Result.class, returnType)) {
            throw t;
        }
        ApiError cause = apiErrorConversionService.getCause(t);
        Result<?> apiResult = buildApiResult(targetMethod, cause);
        Object result = apiConversionService.convert(apiResult, returnType);
        if (result != null) {
            return result;
        }
        throw t;
    }

    private Result<?> buildApiResult(TargetMethod targetMethod, ApiError cause) {
        String code = cause.getCode();
        String message = Optional.ofNullable(targetMethod.getAnnotation(CustomErrorMessage.class))
                .map(CustomErrorMessage::value)
                .filter(StringUtils::hasText)
                .orElse(cause.getMessage());
        return Result.fail(code, message);
    }
}
