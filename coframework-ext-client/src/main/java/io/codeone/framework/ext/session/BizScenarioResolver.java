package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;

/**
 * Interface for resolving a {@link BizScenario} during a method invocation.
 *
 * <p>Used when the resolution policy in {@link ExtensionSession} is {@link BizScenarioResolvePolicy#CUSTOM}
 * or {@link BizScenarioResolvePolicy#AUTO}. For {@code CUSTOM}, the specified custom
 * resolver is used. For {@code AUTO}, the custom resolver is used if specified;
 * otherwise, default resolution applies.
 *
 * <p>Implementations must define how to derive the {@code BizScenario} from method
 * arguments.
 */
public interface BizScenarioResolver {

    /**
     * Resolves a {@link BizScenario} based on the provided method arguments.
     *
     * @param args the method arguments
     * @return the resolved {@code BizScenario}, or {@code null} if resolution is
     * not possible
     */
    BizScenario resolve(Object[] args);
}
