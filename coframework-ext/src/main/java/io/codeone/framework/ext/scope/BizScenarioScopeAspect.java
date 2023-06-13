package io.codeone.framework.ext.scope;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.ext.repo.BizScenarioScopeRepo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
public class BizScenarioScopeAspect {

    @Resource
    private BizScenarioScopeRepo bizScenarioScopeRepo;

    @Around("@within(scope) && !@annotation(io.codeone.framework.ext.scope.BizScenarioScope)")
    public Object aroundClass(ProceedingJoinPoint pjp, BizScenarioScope scope) throws Throwable {
        return around(pjp, scope);
    }

    @Around("@annotation(scope)")
    public Object aroundMethod(ProceedingJoinPoint pjp, BizScenarioScope scope) throws Throwable {
        return around(pjp, scope);
    }

    private Object around(ProceedingJoinPoint pjp, BizScenarioScope scope) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        BizScenario bizScenario = resolveBizScenario(method, pjp.getArgs(), scope);
        if (bizScenario == null) {
            throw new IllegalArgumentException("Could not resolve BizScenario from args of '" + method + "'");
        }
        return BizScenarioContext.invoke(bizScenario, pjp::proceed);
    }

    private BizScenario resolveBizScenario(Method method, Object[] args, BizScenarioScope scope) {
        int index = bizScenarioScopeRepo.getParamIndex(method);
        if (index != -1) {
            return ((BizScenarioParam) args[index]).getBizScenario();
        }

        BizScenarioResolver resolver = bizScenarioScopeRepo.getResolver(scope.customResolver());
        return resolver.resolve(args);
    }
}
