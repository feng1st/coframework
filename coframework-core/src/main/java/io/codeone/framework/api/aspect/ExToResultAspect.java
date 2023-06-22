package io.codeone.framework.api.aspect;

import io.codeone.framework.api.API;
import io.codeone.framework.response.Result;
import io.codeone.framework.util.AspectOrders;
import io.codeone.framework.util.ErrorUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ExToResultAspect will convert any exception thrown by a method to a failed
 * Result, if the return type of that method is a Result, and the class of that
 * method is annotated by '@API'.
 *
 * @see API
 * @see Result
 */
@Aspect
@Order(AspectOrders.WRAPPING_EXCEPTION)
@Component
public class ExToResultAspect {

    @Around("@within(api) && !@annotation(io.codeone.framework.api.API)")
    public Object aroundClass(ProceedingJoinPoint pjp, API api)
            throws Throwable {
        return around(pjp, api);
    }

    @Around("@annotation(api)")
    public Object aroundMethod(ProceedingJoinPoint pjp, API api)
            throws Throwable {
        return around(pjp, api);
    }

    private Object around(ProceedingJoinPoint pjp, API api) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Throwable t) {
            return exToResult(pjp, api, t);
        }
    }

    private Result<?> exToResult(ProceedingJoinPoint pjp, API api, Throwable t)
            throws Throwable {
        Class<?> returnType = ((MethodSignature) pjp.getSignature())
                .getReturnType();
        if (!Result.class.isAssignableFrom(returnType)) {
            throw t;
        }
        try {
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
