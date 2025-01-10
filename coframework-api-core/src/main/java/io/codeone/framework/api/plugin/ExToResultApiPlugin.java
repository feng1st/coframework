package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.util.ApiErrorUtils;
import io.codeone.framework.api.util.FailureUtils;
import io.codeone.framework.common.util.AnnotationUtils;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;

import java.lang.reflect.Method;

/**
 * Plugin for transforming exceptions into failed results for methods annotated
 * with {@link API}.
 *
 * <p>Intercepts thrown exceptions and transforms them into a failed result if applicable.
 */
@Plug(value = Stages.POST_RESULT_INTERCEPTING, targetAnnotations = API.class)
public class ExToResultApiPlugin implements Plugin {

    @Override
    public Object afterThrowing(Method method, Object[] args, Throwable throwable)
            throws Throwable {
        Class<?> returnType = method.getReturnType();
        if (!FailureUtils.isSupported(returnType)) {
            throw throwable;
        }

        ApiError cause = ApiErrorUtils.getCause(throwable);
        CustomErrorMessage customErrorMessage = AnnotationUtils.getAnnotation(method, CustomErrorMessage.class);
        if (customErrorMessage != null) {
            cause = ApiError.of(cause.getCode(), cause.isCritical(), customErrorMessage.value());
        }

        Object result = FailureUtils.toFailure(cause, returnType);
        if (result != null) {
            return result;
        }
        throw throwable;
    }
}
