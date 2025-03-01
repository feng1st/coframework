package io.codeone.framework.ext.annotation;

import java.lang.annotation.*;

/**
 * Indicates that a parameter is used to determine the {@code BizScenario} for routing.
 *
 * <p>When applied to a method parameter, this annotation marks it as the source
 * of the {@code BizScenario} used to locate the appropriate {@code Extension} implementation.
 * The parameter must implement the {@code BizScenarioParam} interface.
 *
 * <p>Usage constraints:
 * <ul>
 *   <li>Only one parameter can be annotated with {@code @RouteBy} per method.</li>
 *   <li>The annotated parameter must implement {@code BizScenarioParam}, or an
 *   error will occur during processing.</li>
 * </ul>
 *
 * <p>If no parameter is annotated with {@code @RouteBy}, other mechanisms such
 * as {@code @RouteByContext} or context-based resolution are used to determine
 * the {@code BizScenario}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RouteBy {
}
