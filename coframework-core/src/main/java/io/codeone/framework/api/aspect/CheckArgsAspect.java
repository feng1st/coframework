package io.codeone.framework.api.aspect;

import io.codeone.framework.request.ApiParam;
import io.codeone.framework.util.AspectOrders;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(AspectOrders.CHECKING_ARGS)
@Component
public class CheckArgsAspect {

    @Before("@within(io.codeone.framework.api.API)")
    public void around(JoinPoint jp) throws Throwable {
        for (Object arg : jp.getArgs()) {
            if (arg instanceof ApiParam) {
                ((ApiParam) arg).checkArgs();
            }
        }
    }
}
