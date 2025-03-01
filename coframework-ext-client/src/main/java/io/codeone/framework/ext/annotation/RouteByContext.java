package io.codeone.framework.ext.annotation;

import java.lang.annotation.*;

/**
 * Indicates that the {@code BizScenario} for routing should be derived from the
 * execution context.
 *
 * <p>When applied at the class or method level, this annotation specifies that
 * the routing mechanism should use the {@code BizScenario} from the current execution
 * context (e.g., {@code BizScenarioContext#getBizScenario()}).
 *
 * <p>Usage details:
 * <ul>
 *   <li>Can be applied to a class, affecting all methods within the class.</li>
 *   <li>Can be applied to individual methods for more granular control.</li>
 *   <li>If both {@code @RouteBy} and {@code @RouteByContext} are used in the same
 *   method, a conflict will occur.</li>
 * </ul>
 *
 * <p>If neither {@code @RouteBy} nor {@code @RouteByContext} is specified, the
 * framework attempts to locate the {@code BizScenario} using a parameter implementing
 * {@code BizScenarioParam} or defaults to the current context.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RouteByContext {
}
