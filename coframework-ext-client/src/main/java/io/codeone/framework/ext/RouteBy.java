package io.codeone.framework.ext;

import io.codeone.framework.ext.session.ExtensionSession;

import java.lang.annotation.*;

/**
 * Use this annotation on parameters of an Extensible interface to specify the
 * Extension routing parameter ({@link BizScenarioParam}) when there are more
 * than one candidate. Please refer to {@code BizScenarioParam} for more
 * information.
 *
 * <p>It is recommended to use {@code ExtensionSession} to get rid of individual
 * routing strategy control and to eliminate code intrusiveness of the
 * {@code BizScenarioParam} parameters, please refer to {@code ExtensionSession}
 * for more information.
 *
 * @see BizScenarioParam
 * @see Extensible
 * @see ExtensionSession
 * @see RouteByContext
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RouteBy {
}
