package io.codeone.framework.ext.scan.impl;

import io.codeone.framework.ext.*;
import io.codeone.framework.ext.repo.BizScenarioParamRepo;
import io.codeone.framework.ext.scan.BaseExtScanner;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Scans all Extensible methods, finds and saves the indexes of Extension
 * routing parameters, in the following order of priority:
 *
 * <ol>
 * <li>If the method takes no parameter, returns -1 which means using the
 * {@link BizScenario} instance at the top of the stack in the
 * {@link BizScenarioContext} for Extension routing.
 * <li>If the method has only one parameter that is a {@link BizScenarioParam}
 * and is annotated by {@link RouteBy}, returns its index. An exception will be
 * thrown if there are more than one {@code RouteBy} in the same method, or the
 * type of the annotated parameter is not {@code BizScenarioParam}.
 * <li>If the method or the Extensible interface itself has the
 * {@link RouteByContext} annotation, returns -1. An exception will be thrown if
 * the method has both {@code RouteBy} and {@code RouteByContext} annotations.
 * <li>If only one of the method's parameters is {@code BizScenarioParam},
 * returns the index of that parameter. An exception will be thrown if there are
 * more than one {@code BizScenarioParam} in the same method.
 * <li>If the {@code coframework.ext.route-by-context-by-default} application
 * property is set as {@code true}, return -1.
 * <li>Otherwise, throws an exception to indicate the failure of the Extension
 * routing parameter finding.
 * </ol>
 */
@Component
public class BizScenarioParamScanner extends BaseExtScanner {

    @Resource
    private BizScenarioParamRepo bizScenarioParamRepo;

    @Value("${coframework.ext.route-by-context-by-default:false}")
    private boolean routeByContextByDefault;

    /**
     * {@inheritDoc}
     */
    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        bizScenarioParamRepo.computeParamIndexIfAbsent(method,
                () -> findBizScenarioParamIndex(extensibleClass, method));
    }

    private int findBizScenarioParamIndex(Class<?> extensibleClass, Method method) {
        if (method.getParameters().length == 0) {
            return -1;
        }

        Integer index = null;
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter param = method.getParameters()[i];
            if (param.isAnnotationPresent(RouteBy.class)) {
                if (index != null) {
                    throw new IllegalStateException("Found duplicate @RouteBys on '" + method + "'");
                }
                if (!ExtUtils.isBizScenarioParam(param.getType())) {
                    throw new IllegalStateException(
                            "The parameter of '" + method + "' annotated by @RouteBy is not a BizScenarioParam");
                }
                index = i;
            }
        }
        if (index != null) {
            if (method.isAnnotationPresent(RouteByContext.class)) {
                throw new IllegalStateException("Found both @RouteBy and @RouteByContext on '" + method + "'");
            }

            return index;
        }

        if (method.isAnnotationPresent(RouteByContext.class)
                || extensibleClass.isAnnotationPresent(RouteByContext.class)) {
            return -1;
        }

        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter param = method.getParameters()[i];
            if (ExtUtils.isBizScenarioParam(param.getType())) {
                if (index != null) {
                    throw new IllegalStateException("Found duplicate BizScenarioParams on '" + method + "'");
                }
                index = i;
            }
        }

        if (index != null) {
            return index;
        }

        if (routeByContextByDefault) {
            return -1;
        }

        throw new IllegalStateException("Could not find consistent BizScenarioParam on '" + method + "'");
    }
}
