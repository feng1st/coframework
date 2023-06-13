package io.codeone.framework.ext.scope;

public enum BizScenarioResolvePolicy {
    AUTO,
    /**
     * Uses the first BizScenarioParam.
     */
    FIRST,
    /**
     * Uses the last BizScenarioParam.
     */
    LAST,
    /**
     * Uses the parameter that annotated by @ResolveFrom.
     */
    SPECIFIED,
    /**
     * Uses BizScenarioResolver to resolve.
     */
    CUSTOM,
    ;
}
