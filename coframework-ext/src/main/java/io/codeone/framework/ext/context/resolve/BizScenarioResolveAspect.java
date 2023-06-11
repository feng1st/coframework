package io.codeone.framework.ext.context.resolve;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.RouteBy;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.ext.resolve.BizScenarioResolve;
import io.codeone.framework.ext.resolve.BizScenarioResolvePolicy;
import io.codeone.framework.ext.resolve.BizScenarioResolver;
import io.codeone.framework.ext.util.ExtUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class BizScenarioResolveAspect {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<Method, Integer> paramIndexCache = new ConcurrentHashMap<>();

    private final Map<Class<? extends BizScenarioResolver>, BizScenarioResolver> resolverCache
            = new ConcurrentHashMap<>();

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
        BizScenario bizScenario = resolveBizScenario(methodSignature.getMethod(), pjp.getArgs(), resolve);
        if (bizScenario == null) {
            throw new IllegalArgumentException("Could not resolve BizScenario from arguments of '"
                    + methodSignature.getMethod() + "'");
        }
        return BizScenarioContext.invoke(bizScenario, pjp::proceed);
    }

    private BizScenario resolveBizScenario(Method method, Object[] args, BizScenarioResolve resolve) {
        if (resolve.value() == BizScenarioResolvePolicy.CUSTOM) {
            BizScenarioResolver resolver = resolverCache.computeIfAbsent(
                    resolve.customResolver(), applicationContext::getBean);
            return resolver.resolve(args);
        } else {
            int paramIndex = paramIndexCache.computeIfAbsent(method, o -> getParamIndex(o, resolve));
            if (paramIndex >= 0) {
                return ((BizScenarioParam) args[paramIndex]).getBizScenario();
            }
        }
        return null;
    }

    private static int getParamIndex(Method method, BizScenarioResolve resolve) {
        // TODO: move any checking to starting-up time

        if (resolve.value() == BizScenarioResolvePolicy.FIRST_PARAM) {
            for (int i = 0; i < method.getParameters().length; i++) {
                if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                    return i;
                }
            }
        } else if (resolve.value() == BizScenarioResolvePolicy.LAST_PARAM) {
            for (int i = method.getParameters().length - 1; i >= 0; i--) {
                if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                    return i;
                }
            }
        } else if (resolve.value() == BizScenarioResolvePolicy.ROUTE_BY) {
            Integer index = null;
            for (int i = 0; i < method.getParameters().length; i++) {
                Parameter param = method.getParameters()[i];
                if (param.getAnnotation(RouteBy.class) != null) {
                    if (index != null) {
                        throw new IllegalStateException("Found duplicate @RouteBys on '" + method + "'");
                    }
                    if (!ExtUtils.isBizScenarioParam(param.getType())) {
                        throw new IllegalStateException("The parameter of '" + method
                                + "' annotated by @RouteBy is not a BizScenarioParam");
                    }
                    index = i;
                }
            }
            if (index != null) {
                return index;
            }
        }

        return -1;
    }
}
