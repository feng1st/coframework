package io.codeone.framework.ext.resolve;

public enum BizScenarioResolvePolicy {
    /**
     * Uses the first BizScenarioParam.
     */
    FIRST_PARAM,
    /**
     * Uses the last BizScenarioParam.
     */
    LAST_PARAM,
    /**
     * Uses the parameter that annotated by @ResolveBy.
     */
    SPECIFIED,
    /**
     * Uses BizScenarioResolver to resolve.
     */
    CUSTOM,
    ;
}
