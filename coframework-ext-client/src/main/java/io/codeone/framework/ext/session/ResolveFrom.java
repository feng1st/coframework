package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;

import java.lang.annotation.*;

/**
 * Used with {@link BizScenarioResolvePolicy#SPECIFIED} or
 * {@link BizScenarioResolvePolicy#AUTO} to specify a {@link BizScenarioParam}
 * parameter that a {@link BizScenario} object can be resolved from.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ResolveFrom {
}
