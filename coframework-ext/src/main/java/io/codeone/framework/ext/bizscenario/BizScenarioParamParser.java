package io.codeone.framework.ext.bizscenario;

import io.codeone.framework.ext.annotation.RouteBy;
import io.codeone.framework.ext.annotation.RouteByContext;
import io.codeone.framework.ext.util.ExtUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Utility for analyzing and determining the {@code BizScenarioParam} index in method
 * signatures.
 *
 * <p>This class supports parameter resolution strategies, including annotations
 * ({@link RouteBy} and {@link RouteByContext}) and type inference.
 */
@UtilityClass
@Slf4j(topic = "extension")
public class BizScenarioParamParser {

    /**
     * Parses the parameter index for the {@code BizScenarioParam} in the specified
     * method.
     *
     * @param extensibleInterface the extensible interface
     * @param method              the method to analyze
     * @return the parameter index or {@link BizScenarioParamRepo#INDEX_ROUTE_BY_CONTEXT}
     * if resolved from the context
     * @throws IllegalStateException if conflicting or invalid annotations are found
     */
    public int parseParamIndex(Class<?> extensibleInterface, Method method) {
        if (method.getParameters().length == 0) {
            return BizScenarioParamRepo.INDEX_ROUTE_BY_CONTEXT;
        }

        Integer index = null;
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter param = method.getParameters()[i];
            if (param.isAnnotationPresent(RouteBy.class)) {
                if (index != null) {
                    throw new IllegalStateException(String.format(
                            "Duplicate @RouteBy parameters found on method '%s'",
                            method));
                }
                if (!ExtUtils.isBizScenarioParam(param.getType())) {
                    throw new IllegalStateException(String.format(
                            "Parameter annotated with @RouteBy is not BizScenarioParam on method '%s'",
                            method));
                }
                index = i;
            }
        }
        if (index != null) {
            if (method.isAnnotationPresent(RouteByContext.class)) {
                throw new IllegalStateException(String.format(
                        "Conflicting annotations @RouteBy and @RouteByContext on method '%s'",
                        method));
            }
            return index;
        }

        if (method.isAnnotationPresent(RouteByContext.class)
                || extensibleInterface.isAnnotationPresent(RouteByContext.class)) {
            return BizScenarioParamRepo.INDEX_ROUTE_BY_CONTEXT;
        }

        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter param = method.getParameters()[i];
            if (ExtUtils.isBizScenarioParam(param.getType())) {
                if (index != null) {
                    throw new IllegalStateException(String.format(
                            "Duplicate BizScenarioParam parameters found on method '%s'",
                            method));
                }
                index = i;
            }
        }
        if (index != null) {
            return index;
        }

        log.warn("The method in the Extensible interface does not include routing parameters or annotations. Defaulting to routing by context: \"{}\"", method);

        return BizScenarioParamRepo.INDEX_ROUTE_BY_CONTEXT;
    }
}
