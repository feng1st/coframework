package io.codeone.framework.ext.repo;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.session.BizScenarioResolver;
import io.codeone.framework.ext.session.ExtensionSession;
import io.codeone.framework.ext.session.ExtensionSessionIndexer;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * Repository to store and access the zero-based index of the
 * {@link BizScenarioParam} parameter used for an {@link ExtensionSession} entry
 * method, which contains the {@link BizScenario} instance for future Extension
 * routing. A -1 value indicates that there is no such parameter and the
 * framework will use {@link ExtensionSession#customResolver()} to resolve the
 * {@code BizScenario} instance.
 *
 * @see ExtensionSession
 * @see ExtensionSessionIndexer
 */
public interface ExtensionSessionRepo {

    /**
     * Computes and stores the index of the {@link BizScenarioParam} parameter.
     * Please refer to {@link ExtensionSessionIndexer} for more information.
     *
     * @param method   the entry method of an Extension session
     * @param supplier the supplier that returns the index of the
     *                 {@code BizScenarioParam} parameter which contains the
     *                 {@link BizScenario} instance used for future Extension
     *                 routing, or -1 indicating that a
     *                 {@link BizScenarioResolver} will be used for
     *                 {@code BizScenario} resolving
     */
    void computeParamIndexIfAbsent(Method method, Supplier<Integer> supplier);

    /**
     * Returns the zero-based index of the {@link BizScenarioParam} parameter
     * which contains the {@link BizScenario} instance used for future Extension
     * routing, or -1 indicating that a {@link BizScenarioResolver} will be used
     * for {@code BizScenario} resolving.
     *
     * @param method the entry method of an Extension session
     * @return the index of the {@code BizScenarioParam} parameter, or -1
     * indicating that a {@code BizScenarioResolver} will be used for
     * {@code BizScenario} resolving
     */
    int getParamIndex(Method method);

    /**
     * Caches and returns the {@link BizScenarioResolver} instance bean of the
     * specified {@code BizScenarioResolver} class.
     *
     * @param clazz the class of the specific {@code BizScenarioResolver}
     * @return the instance bean of the given {@code BizScenarioResolver}
     */
    BizScenarioResolver getResolver(Class<? extends BizScenarioResolver> clazz);
}
