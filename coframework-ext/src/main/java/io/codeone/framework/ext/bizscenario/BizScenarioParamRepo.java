package io.codeone.framework.ext.bizscenario;

import java.lang.reflect.Method;

/**
 * Repository for determining the index of a {@code BizScenarioParam} in a method's
 * parameter list.
 *
 * <p>This repository helps map methods of {@code Extensible} interfaces to their
 * {@code BizScenario} sources, either through parameter annotations or context-based
 * resolution.
 */
public interface BizScenarioParamRepo {

    /**
     * Constant indicating that the {@code BizScenario} should be resolved from
     * the context.
     */
    int INDEX_ROUTE_BY_CONTEXT = -1;

    /**
     * Gets the parameter index for the {@code BizScenarioParam} in the specified
     * method.
     *
     * @param method the method to analyze
     * @return the parameter index or {@code #INDEX_ROUTE_BY_CONTEXT} if the {@code
     * BizScenario} is resolved from the context
     * @throws IllegalStateException if no {@code BizScenario} source is registered
     *                               for the method
     */
    int getParamIndex(Method method);
}
