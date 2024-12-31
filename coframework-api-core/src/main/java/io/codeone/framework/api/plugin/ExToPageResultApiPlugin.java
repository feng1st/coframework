package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.ApiErrorCode;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.response.PageResult;
import io.codeone.framework.api.util.ApiErrorCodeUtils;
import io.codeone.framework.common.util.AnnotationUtils;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;

import java.lang.reflect.Method;

/**
 * Plugin for transforming exceptions into {@link PageResult} for methods annotated
 * with {@link API}.
 *
 * <p>Intercepts thrown exceptions and transforms them into a failed {@link PageResult}
 * if the return type is {@link PageResult}.
 */
@Plug(value = Stages.POST_RESULT_INTERCEPTING, targetAnnotations = API.class)
public class ExToPageResultApiPlugin implements Plugin {

    @Override
    public Object afterThrowing(Method method, Object[] args, Throwable throwable)
            throws Throwable {
        Class<?> returnType = method.getReturnType();
        if (returnType != PageResult.class) {
            throw throwable;
        }
        Throwable cause = ApiErrorCodeUtils.getCause(throwable);
        ApiErrorCode apiErrorCode = ApiErrorCodeUtils.toApiErrorCode(cause);
        String code = apiErrorCode.getCode();
        String message = cause.getMessage();
        CustomErrorMessage customErrorMessage = AnnotationUtils.getAnnotation(method, CustomErrorMessage.class);
        if (customErrorMessage != null) {
            message = customErrorMessage.value();
        }
        return PageResult.failure(code, message);
    }
}
