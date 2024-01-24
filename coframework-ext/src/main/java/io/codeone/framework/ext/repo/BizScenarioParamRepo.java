package io.codeone.framework.ext.repo;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioContext;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.scan.impl.BizScenarioParamScanner;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * Repository to store and access the zero-based index of the
 * {@link BizScenarioParam} parameter of an Extensible method, which contains
 * the {@link BizScenario} instance to be used for Extension routing. A -1 value
 * indicates that there is no such parameter but {@link BizScenarioContext} is
 * where the {@code BizScenario} instance should be looked for. Please refer to
 * {@link BizScenarioParamScanner} for more information.
 *
 * @see BizScenarioParamScanner
 */
public interface BizScenarioParamRepo {

    /**
     * Computes and stores the index of the {@link BizScenarioParam} parameter.
     * Please refer to {@link BizScenarioParamScanner} for more information.
     *
     * @param method   the Extensible method to be routed
     * @param supplier the supplier that returns the index of the
     *                 {@code BizScenarioParam} parameter which contains the
     *                 {@link BizScenario} instance used for Extension routing,
     *                 or -1 indicating that the {@code BizScenario} instance
     *                 should be acquired from {@link BizScenarioContext}
     */
    void computeParamIndexIfAbsent(Method method, Supplier<Integer> supplier);

    /**
     * Returns the zero-based index of the {@link BizScenarioParam} parameter
     * which contains the {@link BizScenario} instance used for Extension
     * routing, or -1 indicating that the {@code BizScenario} instance should be
     * acquired from {@link BizScenarioContext}.
     *
     * @param method the Extensible method to be routed
     * @return the index of the {@code BizScenarioParam} parameter, or -1
     * indicating that the {@code BizScenario} instance should be acquired from
     * {@code BizScenarioContext}
     */
    int getParamIndex(Method method);
}
