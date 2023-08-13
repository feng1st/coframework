package io.codeone.framework.ext;

import io.codeone.framework.ext.session.ExtensionSession;

/**
 * Represents an Extension routing parameter and its {@link #getBizScenario()}
 * attribute will be used for Extension routing.
 *
 * <p>{@link BizScenario} is also a {@code BizScenarioParam}.
 *
 * <p>In {@code coframework-api} there is a {@code BaseRequest} and a
 * {@code PageRequest} which implemented this interface, and you can use them as
 * the base class of all your requests (recommended).
 *
 * <p>It is recommended to use {@link ExtensionSession} to eliminate code
 * intrusiveness of the {@code BizScenarioParam} parameters to Extensible
 * interfaces, please refer to {@code ExtensionSession} for more information.
 *
 * @see ExtensionSession
 */
public interface BizScenarioParam {

    /**
     * Returns the {@link BizScenario} instance for Extension routing.
     *
     * @return the {@code BizScenario} instance for Extension routing
     */
    BizScenario getBizScenario();
}
