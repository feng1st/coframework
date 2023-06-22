package io.codeone.framework.api.interceptors;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiInterceptor;
import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Stage;
import io.codeone.framework.intercept.util.Signature;
import io.codeone.framework.response.Result;
import io.codeone.framework.util.ErrorUtils;
import org.springframework.stereotype.Component;

@Component
@Intercept(Stage.EXCEPTION_HANDLING)
public class ExToResultApiInterceptor implements ApiInterceptor<Void> {

    @Override
    public Object afterThrowing(Signature signature, Object[] args,
                                Throwable error) throws Throwable {
        return exToResult(signature, error);
    }

    private Result<?> exToResult(Signature signature, Throwable t)
            throws Throwable {
        Class<?> returnType = signature.getReturnType();
        if (!Result.class.isAssignableFrom(returnType)) {
            throw t;
        }
        try {
            API api = signature.getAnnotation(API.class);
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
