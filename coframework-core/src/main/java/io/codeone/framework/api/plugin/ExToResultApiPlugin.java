package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiConstants;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.TargetMethod;
import io.codeone.framework.response.Result;
import io.codeone.framework.util.ErrorUtils;

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

    private Result<?> exToResult(TargetMethod targetMethod, Throwable t)
            throws Throwable {
        Class<?> returnType = targetMethod.getReturnType();
        if (!Result.class.isAssignableFrom(returnType)) {
            throw t;
        }
        try {
            API api = targetMethod.getAnnotation(API.class);
            return buildResult(t, api);
        } catch (Exception e) {
            throw t;
        }
    }

    private Result<?> buildResult(Throwable t, API api) {
        Throwable cause = ErrorUtils.getCause(t);
        String code = ErrorUtils.getCode(cause);
        String message = !api.errorMessage().isEmpty() ? api.errorMessage() : cause.getMessage();
        return Result.fail(code, message);
    }
}
