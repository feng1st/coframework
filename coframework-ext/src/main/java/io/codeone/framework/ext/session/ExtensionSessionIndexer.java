package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;

import java.lang.reflect.Method;

/**
 * Computes and stores the indexes of the {@link BizScenarioParam} parameters or
 * the custom {@link BizScenarioResolver} used for {@link ExtensionSession}
 * entry methods, and uses them to resolve the {@link BizScenario} instance used
 * for future Extension routing later.
 */
public interface ExtensionSessionIndexer {

    /**
     * Computes and stores the indexes of the {@link BizScenarioParam}
     * parameters or the custom {@link BizScenarioResolver} used for
     * {@link ExtensionSession} entry methods.
     *
     * @param method     the entry method of an Extension session
     * @param annotation the {@code ExtensionSession} annotation with resolving
     *                   policy
     */
    void index(Method method, ExtensionSession annotation);

    /**
     * Resolves the {@link BizScenario} instance used for future Extension
     * routing from the indexed {@link BizScenarioParam} or
     * {@link BizScenarioResolver}.
     *
     * @param method  the entry method of an Extension session
     * @param args    the arguments of the entry method
     * @param session the {@code ExtensionSession} annotation with resolving
     *                policy
     * @return resolved {@code BizScenario} instance used for future Extension
     * routing
     */
    BizScenario resolve(Method method, Object[] args, ExtensionSession session);
}
