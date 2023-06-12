package io.codeone.framework.ext.context.resolve;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.ext.repo.BizScenarioResolveRepo;
import io.codeone.framework.ext.resolve.BizScenarioResolve;
import io.codeone.framework.ext.resolve.BizScenarioResolvePolicy;
import io.codeone.framework.ext.resolve.BizScenarioResolver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
public class BizScenarioResolveAspect {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private BizScenarioResolveRepo bizScenarioResolveRepo;

    @Around("@within(resolve) && !@annotation(io.codeone.framework.ext.resolve.BizScenarioResolve)")
    public Object aroundClass(ProceedingJoinPoint pjp, BizScenarioResolve resolve) throws Throwable {
        return around(pjp, resolve);
    }

    @Around("@annotation(resolve)")
    public Object aroundMethod(ProceedingJoinPoint pjp, BizScenarioResolve resolve) throws Throwable {
        return around(pjp, resolve);
    }

    private Object around(ProceedingJoinPoint pjp, BizScenarioResolve resolve) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        BizScenario bizScenario = resolveBizScenario(method, pjp.getArgs(), resolve);
        if (bizScenario == null) {
            throw new IllegalArgumentException("Could not resolve BizScenario from args of '" + method + "'");
        }
        return BizScenarioContext.invoke(bizScenario, pjp::proceed);
    }

    private BizScenario resolveBizScenario(Method method, Object[] args, BizScenarioResolve resolve) {
        if (resolve.value() == BizScenarioResolvePolicy.CUSTOM) {
            BizScenarioResolver resolver = bizScenarioResolveRepo.getResolver(resolve.customResolver());
            return resolver.resolve(args);
        } else {
            int paramIndex = bizScenarioResolveRepo.getParamIndex(method);
            return ((BizScenarioParam) args[paramIndex]).getBizScenario();
        }
    }
}
