package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.api.util.ApiExceptionUtils;
import io.codeone.framework.common.util.AnnotationUtils;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;

import java.lang.reflect.Method;

/**
 * Plugin for transforming exceptions into {@link Result} for methods annotated
 * with {@link API}.
 *
 * <p>Intercepts thrown exceptions and transforms them into a failed {@link Result}
 * if the return type is {@link Result}.
 */
@Plug(value = Stages.POST_RESULT_INTERCEPTING, targetAnnotations = API.class)
public class ExToResultApiPlugin implements Plugin {

    @Override
    public Object afterThrowing(Method method, Object[] args, Throwable throwable)
            throws Throwable {
        Class<?> returnType = method.getReturnType();
        if (returnType != Result.class) {
            throw throwable;
        }
        Throwable cause = ApiExceptionUtils.getCause(throwable);
        String code = ApiExceptionUtils.getCode(cause);
        String message = cause.getMessage();
        CustomErrorMessage customErrorMessage = AnnotationUtils.getAnnotation(method, CustomErrorMessage.class);
        if (customErrorMessage != null) {
            message = customErrorMessage.value();
        }
        return Result.failure(code, message);
    }
}