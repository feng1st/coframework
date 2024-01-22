package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiConstants;
import io.codeone.framework.exception.ApiError;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.ConversionServiceUtil;
import io.codeone.framework.plugin.util.ErrorServiceUtil;
import io.codeone.framework.plugin.util.TargetMethod;
import io.codeone.framework.response.Result;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * {@code ExToResultApiPlugin} will convert any exception to a failed
 * {@link Result}, if the return type of the API (service/method annotated by
 * {@link API}) method is {@code Result}.
 *
 * @see API
 * @see Result
 */
@Plug(value = Stages.POST_RESULT_INTERCEPTING, group = ApiConstants.PLUGIN_GROUP)
public class ExToResultApiPlugin implements Plugin {

    @Resource
    private ConversionServiceUtil conversionServiceUtil;

    @Resource
    private ErrorServiceUtil errorServiceUtil;

    /**
     * If an exception had been thrown and the return type of the API method is
     * {@link Result}, the exception will be wrapped as a failed {@code Result}.
     * And the error message of the wrapped result will be replaced by
     * {@link API#errorMessage()} if which is not empty.
     *
     * <p>{@inheritDoc}
     */
    @Override
    public Object afterThrowing(TargetMethod targetMethod, Object[] args, Throwable error)
            throws Throwable {
        return exToResult(targetMethod, error);
    }

    private Object exToResult(TargetMethod targetMethod, Throwable t)
            throws Throwable {
        try {
            Result<?> apiResult = buildApiResult(targetMethod, t);
            Object result = conversionServiceUtil.convert(apiResult, targetMethod.getReturnType());
            if (result != null) {
                return result;
            }
        } catch (Exception ignored) {
        }
        throw t;
    }

    private Result<?> buildApiResult(TargetMethod targetMethod, Throwable t) {
        ApiError cause = errorServiceUtil.getCause(t);
        String code = cause.getCode();
        String message = Optional.ofNullable(targetMethod.getAnnotation(API.class))
                .map(API::errorMessage)
                .filter(StringUtils::hasText)
                .orElse(cause.getMessage());
        return Result.fail(code, message);
    }
}
