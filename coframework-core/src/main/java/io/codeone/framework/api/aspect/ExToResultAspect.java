package io.codeone.framework.api.aspect;

import io.codeone.framework.response.Result;
import io.codeone.framework.util.AspectOrders;
import io.codeone.framework.util.ErrorUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(AspectOrders.WRAPPING_EXCEPTION)
@Component
public class ExToResultAspect {

    @Around("@within(io.codeone.framework.api.API)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Throwable t) {
            return exToResult(pjp, t);
        }
    }

    private Result<?> exToResult(ProceedingJoinPoint pjp, Throwable t) throws Throwable {
        Class<?> returnType = ((MethodSignature) pjp.getSignature()).getReturnType();
        if (!Result.class.isAssignableFrom(returnType)) {
            throw t;
        }
        try {
            return buildResult(t, returnType);
        } catch (Exception e) {
            throw t;
        }
    }

    private Result<?> buildResult(Throwable t, Class<?> returnType)
            throws InstantiationException, IllegalAccessException {
        Throwable cause = ErrorUtils.getCause(t);
        Result<?> result = (Result<?>) returnType.newInstance();
        result.setSuccess(false);
        result.setErrorCode(ErrorUtils.getCode(cause));
        result.setErrorMessage(cause.getMessage());
        return result;
    }
}
