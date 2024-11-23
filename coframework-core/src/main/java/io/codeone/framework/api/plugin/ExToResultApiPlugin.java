package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.api.util.ApiExceptionUtils;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.AnnotationUtils;

import java.lang.reflect.Method;

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
