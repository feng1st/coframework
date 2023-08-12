package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;

/**
 * Interface of all custom {@link BizScenario} resolvers. It is used with
 * {@link BizScenarioResolvePolicy#CUSTOM} or
 * {@link BizScenarioResolvePolicy#AUTO} to resolve a {@code BizScenario} object
 * from arguments of the service/method which annotated by
 * {@code ExtensionSession}. Please refer to {@code ExtensionSession} for more
 * information.
 *
 * @see ExtensionSession
 */
public interface BizScenarioResolver {

    /**
     * Resolves a {@link BizScenario} object from arguments of the
     * service/method which annotated by {@link ExtensionSession}.
     *
     * @param args the arguments which a {@code BizScenario} object can be
     *             resolved from
     * @return the resolved {@code BizScenario} object
     */
    BizScenario resolve(Object[] args);
}
