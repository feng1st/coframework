package io.codeone.framework.ext.scope;

import io.codeone.framework.ext.repo.BizScenarioScopeRepo;
import io.codeone.framework.ext.util.ExtUtils;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

@Component
public class BizScenarioScopePlugger
        extends AnnotationMethodPlugger<BizScenarioScope> {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private BizScenarioScopeRepo bizScenarioScopeRepo;

    @Resource
    private BizScenarioScopePlugin plugin;

    @Override
    protected Class<BizScenarioScope> getAnnotationType() {
        return BizScenarioScope.class;
    }

    @Override
    protected List<Plugin<?>> getPlugins(Method method, BizScenarioScope annotation) {
        bizScenarioScopeRepo.computeParamIndexIfAbsent(method,
                k -> getParamIndex(method, annotation));
        return Collections.singletonList(plugin);
    }

    private int getParamIndex(Method method, BizScenarioScope scope) {
        if (scope.value() == BizScenarioResolvePolicy.FIRST) {
            return tryFirst(method);
        }
        if (scope.value() == BizScenarioResolvePolicy.LAST) {
            return tryLast(method);
        }
        if (scope.value() == BizScenarioResolvePolicy.SPECIFIED) {
            return trySpecified(method);
        }
        if (scope.value() == BizScenarioResolvePolicy.CUSTOM) {
            return tryCustomResolver(method, scope);
        }
        // BizScenarioResolvePolicy.AUTO
        try {
            return tryCustomResolver(method, scope);
        } catch (Exception ignored) {
        }
        try {
            return trySpecified(method);
        } catch (Exception ignored) {
        }
        return tryFirst(method);
    }

    private int tryFirst(Method method) {
        for (int i = 0; i < method.getParameters().length; i++) {
            if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                return i;
            }
        }
        throw new IllegalStateException("Could not find BizScenarioParam on '" + method + "'");
    }

    private int tryLast(Method method) {
        for (int i = method.getParameters().length - 1; i >= 0; i--) {
            if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                return i;
            }
        }
        throw new IllegalStateException("Could not find BizScenarioParam on '" + method + "'");
    }

    private int trySpecified(Method method) {
        Integer index = null;
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter param = method.getParameters()[i];
            if (param.isAnnotationPresent(ResolveFrom.class)) {
                if (index != null) {
                    throw new IllegalStateException("Found duplicate @ResolveFroms on '" + method + "'");
                }
                if (!ExtUtils.isBizScenarioParam(param.getType())) {
                    throw new IllegalStateException(
                            "The parameter of '" + method + "' annotated by @ResolveFrom is not a BizScenarioParam");
                }
                index = i;
            }
        }
        if (index == null) {
            throw new IllegalStateException(
                    "Could not find BizScenarioParam annotated by @ResolveFrom on '" + method + "'");
        }
        return index;
    }

    private int tryCustomResolver(Method method, BizScenarioScope scope) {
        try {
            if (scope.customResolver() == BizScenarioResolver.class) {
                throw new IllegalStateException("Did not specify BizScenarioResolver for '" + method + "'");
            }
            BizScenarioResolver ignored = applicationContext.getBean(scope.customResolver());
        } catch (Exception e) {
            throw new IllegalStateException("Could not find consistent BizScenarioResolver on '" + method + "'");
        }
        return -1;
    }
}
