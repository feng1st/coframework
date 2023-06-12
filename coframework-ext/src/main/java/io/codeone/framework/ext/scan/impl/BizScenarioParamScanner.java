package io.codeone.framework.ext.scan.impl;

import io.codeone.framework.ext.RouteBy;
import io.codeone.framework.ext.RouteByContext;
import io.codeone.framework.ext.repo.BizScenarioParamRepo;
import io.codeone.framework.ext.scan.BaseExtScanner;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
public class BizScenarioParamScanner extends BaseExtScanner {

    @Resource
    private BizScenarioParamRepo bizScenarioParamRepo;

    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        int index = findBizScenarioParamIndex(extensibleClass, method);
        bizScenarioParamRepo.putParamIndex(method, index);
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
                    throw new IllegalStateException("The parameter of '" + method
                            + "' annotated by @RouteBy is not a BizScenarioParam");
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

        if (index == null) {
            throw new IllegalStateException("Could not find BizScenarioParam on '" + method + "'");
        }

        return index;
    }
}
