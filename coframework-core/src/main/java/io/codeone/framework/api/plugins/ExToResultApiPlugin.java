package io.codeone.framework.api.plugins;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiPlugin;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.MethodWrap;
import io.codeone.framework.response.Result;
import io.codeone.framework.util.ErrorUtils;

/**
 * ExToResultApiPlugin will convert any exception thrown by a method to a
 * failed Result, if the return type of that method is a Result, and the class
 * of that method is annotated by '@API'.
 *
 * @see API
 * @see Result
 */
@Plug(Stages.EXCEPTION_HANDLING)
public class ExToResultApiPlugin implements ApiPlugin {

    @Override
    public Object afterThrowing(MethodWrap methodWrap, Object[] args, Throwable error)
            throws Throwable {
        return exToResult(methodWrap, error);
    }

    private Result<?> exToResult(MethodWrap methodWrap, Throwable t)
            throws Throwable {
        Class<?> returnType = methodWrap.getReturnType();
        if (!Result.class.isAssignableFrom(returnType)) {
            throw t;
        }
        try {
            API api = methodWrap.getAnnotation(API.class);
            return buildResult(t, returnType, api);
        } catch (Exception e) {
            throw t;
        }
    }

    private Result<?> buildResult(Throwable t, Class<?> returnType, API api)
            throws InstantiationException, IllegalAccessException {
        Throwable cause = ErrorUtils.getCause(t);
        Result<?> result = (Result<?>) returnType.newInstance();
        result.setSuccess(false);
        result.setErrorCode(ErrorUtils.getCode(cause));
        if (!api.errorMessage().isEmpty()) {
            result.setErrorMessage(api.errorMessage());
        } else {
            result.setErrorMessage(cause.getMessage());
        }
        return result;
    }
}
