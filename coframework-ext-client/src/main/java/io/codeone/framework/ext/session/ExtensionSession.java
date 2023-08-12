package io.codeone.framework.ext.session;

import io.codeone.framework.ext.*;

import java.lang.annotation.*;

/**
 * Add this annotation to any service/method, to start an extension session
 * whenever that service/method is invoked. In the session, the framework will
 * try to resolve a {@link BizScenario} instance from the arguments of that
 * method call, and push it to the {@code BizScenarioContext} (in
 * {@code coframework-ext}). By doing this, any subsequent invocation of an
 * Extensible (for example an interface annotated by {@link Ability}) does not
 * have to use a {@link BizScenarioParam} argument explicitly to route a
 * concrete Extension (an implementation of the Extensible, to be specific, a
 * class implemented the Extensible interface and annotated by
 * {@link Extension}).
 *
 * <p>{@code ExtensionSession} helps to curb the intrusiveness of the
 * {@code BizScenarioParam} parameters to the system. All Extensible interfaces
 * do not need an extra or inherited {@code BizScenarioParam} parameter to
 * function properly.
 *
 * <p>How to use {@code ExtensionSession}:
 * <ul>
 * <li>Set application property
 * {@code coframework.ext.route-by-context-by-default} to {@code true}
 * (recommended), or add {@link RouteByContext} to every Extensible interfaces.
 * <li>Add {@code ExtensionSession} annotation to your entrance services, such
 * as facades, rpc services or domain services.
 * <li>Make sure a {@code BizScenario} instance can be resolved from the
 * arguments of these services, by providing a {@code BizScenarioParam}
 * parameter (simpler) or by using a custom {@link BizScenarioResolver},
 * otherwise an exception will be thrown.
 * </ul>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface ExtensionSession {

    /**
     * Specifies how to resolve a {@link BizScenario} instance from the
     * arguments. The default value is {@link BizScenarioResolvePolicy#AUTO}.
     *
     * @return the policy of {@code BizScenario} resolving
     * @see BizScenarioResolvePolicy
     */
    BizScenarioResolvePolicy value() default BizScenarioResolvePolicy.AUTO;

    /**
     * If {@link #value()} is {@link BizScenarioResolvePolicy#CUSTOM}, or
     * {@code value()} is {@link BizScenarioResolvePolicy} and this
     * property is a subclass of {@link BizScenarioResolver}, uses the bean of
     * that subclass to resolve {@link BizScenario} instances.
     *
     * @return a custom {@code BizScenarioResolver} that can resolve
     * {@code BizScenario} instances from arguments
     */
    Class<? extends BizScenarioResolver> customResolver() default BizScenarioResolver.class;
}
