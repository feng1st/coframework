package io.codeone.framework.api;

import io.codeone.framework.exception.ApiError;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.request.ApiParam;
import io.codeone.framework.response.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class ApiAspect {

    @Around("@within(io.codeone.framework.api.API)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        try {
            checkArgs(pjp);
            return pjp.proceed();
        } catch (Throwable t) {
            return processException(pjp, t);
        }
    }

    private void checkArgs(ProceedingJoinPoint pjp) {
        for (Object arg : pjp.getArgs()) {
            if (arg instanceof ApiParam) {
                ((ApiParam) arg).checkArgs();
            }
        }
    }

    private Result<?> processException(ProceedingJoinPoint pjp, Throwable t) throws Throwable {
        Class<?> returnType = ((MethodSignature) pjp.getSignature()).getReturnType();
        if (!Result.class.isAssignableFrom(returnType)) {
            throw t;
        }
        Throwable cause = getCause(t);
        try {
            return exToResult(cause, returnType);
        } catch (Exception e) {
            throw t;
        }
    }

    private Throwable getCause(Throwable t) {
        Set<Throwable> set = new HashSet<>();
        set.add(t);
        while (!(t instanceof ApiError
                || t instanceof IllegalArgumentException)) {
            Throwable cause = t.getCause();
            if (cause == null || !set.add(cause)) {
                break;
            }
            t = cause;
        }
        return t;
    }

    private Result<?> exToResult(Throwable t, Class<?> returnType)
            throws InstantiationException, IllegalAccessException {
        String code;
        if (t instanceof ApiError) {
            code = ((ApiError) t).getCode();
        } else if (t instanceof IllegalArgumentException) {
            code = CommonErrors.INVALID_PARAM.getCode();
        } else {
            code = t.getClass().getSimpleName();
        }

        Result<?> result = (Result<?>) returnType.newInstance();
        result.setSuccess(false);
        result.setErrorCode(code);
        result.setErrorMessage(t.getMessage());
        return result;
    }
}
