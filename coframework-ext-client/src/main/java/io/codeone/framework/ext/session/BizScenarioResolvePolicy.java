package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;

/**
 * Specifies how to resolve a {@link BizScenario} object for an
 * {@code ExtensionSession}. Please refer to {@code ExtensionSession} for more
 * information.
 *
 * @see ExtensionSession
 */
public enum BizScenarioResolvePolicy {
    /**
     * Resolves a {@code BizScenario} object from the arguments, by trying
     * approaches equivalent to {@link #CUSTOM}, {@link #SPECIFIED} and
     * {@link #FIRST} in turn (whichever succeeds first).
     */
    AUTO,
    /**
     * Uses the first {@link BizScenarioParam} argument.
     */
    FIRST,
    /**
     * Uses the last {@code BizScenarioParam} argument.
     */
    LAST,
    /**
     * Uses the {@code BizScenarioParam} argument that annotated by
     * {@link ResolveFrom}.
     */
    SPECIFIED,
    /**
     * Uses the bean of {@link ExtensionSession#customResolver()} to resolve a
     * {@code BizScenario} object from the arguments, if the
     * {@code customResolver()} is a subclass of {@link BizScenarioResolver}.
     */
    CUSTOM,
    ;
}
