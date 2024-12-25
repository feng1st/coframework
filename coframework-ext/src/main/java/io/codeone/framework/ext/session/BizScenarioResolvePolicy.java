package io.codeone.framework.ext.session;

/**
 * Defines the available policies for resolving a {@code BizScenario}.
 *
 * <p>These policies dictate how the framework selects or resolves the {@code BizScenario}
 * during a method invocation.
 */
public enum BizScenarioResolvePolicy {
    /**
     * Automatically resolves the {@code BizScenario} by applying the following
     * steps in order:
     * <ol>
     *   <li>If {@code ExtensionSession#customResolver()} specifies a custom resolver,
     *   it is used to resolve the {@code BizScenario}.
     *   <li>If a method parameter is annotated with {@code RouteBy}, its {@code
     *   BizScenario} is used.
     *   <li>If the first parameter of type {@code BizScenarioParam} exists, it
     *   is used.
     *   <li>If none of the above applies, the {@code BizScenario} is ignored.
     * </ol>
     */
    AUTO,
    /**
     * Uses the first {@code BizScenario} found in the method arguments.
     */
    FIRST,
    /**
     * Uses the last {@code BizScenario} found in the method arguments.
     */
    LAST,
    /**
     * Resolves using a parameter annotated with {@code @RouteBy}.
     */
    SPECIFIED,
    /**
     * Resolves using a custom implementation of {@link BizScenarioResolver}.
     */
    CUSTOM,
    /**
     * Ignores {@code BizScenario} resolution entirely.
     */
    IGNORE,
}
