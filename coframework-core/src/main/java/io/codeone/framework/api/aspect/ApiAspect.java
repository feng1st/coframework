package io.codeone.framework.api.aspect;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiInterceptorChain;
import io.codeone.framework.request.ApiParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * CheckArgsAspect will execute the checkArgs() method of 'ApiParam' type args,
 * upon the invocation of a method of a service, which is annotated by '@API'.
 *
 * @see API
 * @see ApiParam
 */
@Aspect
@Order(0)
@Component
public class ApiAspect {

    @Resource
    private ApiInterceptorChain apiInterceptorChain;

    @Around("@within(io.codeone.framework.api.API)"
            + " || @annotation(io.codeone.framework.api.API)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        return apiInterceptorChain.intercept(method, pjp.getArgs(),
                pjp::proceed);
    }
}
