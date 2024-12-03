package io.codeone.framework.ext.session;

import java.lang.annotation.*;

/**
 * Configures how a {@code BizScenario} is resolved during a method invocation.
 *
 * <p>This annotation can be applied at the class or method level to specify the
 * resolution policy and optionally a custom resolver for determining the {@code
 * BizScenario}. It influences how the framework selects the appropriate {@code
 * Extension} implementation.
 *
 * <p>When applied to a class, it acts as a default for all methods within the class.
 * Method-level annotations can override the class-level configuration.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface ExtensionSession {

    /**
     * Defines the resolution policy for determining the {@code BizScenario}.
     *
     * <p>The default policy is {@link BizScenarioResolvePolicy#AUTO}, which automatically
     * determines the {@code BizScenario} based on the method parameters, context,
     * or the specified {@code customResolver} if provided.
     *
     * @return the resolution policy
     */
    BizScenarioResolvePolicy value() default BizScenarioResolvePolicy.AUTO;

    /**
     * Specifies a custom {@link BizScenarioResolver} for resolving the {@code BizScenario}.
     *
     * <p>This field is used when the resolution policy is set to {@link BizScenarioResolvePolicy#CUSTOM}
     * or {@link BizScenarioResolvePolicy#AUTO}, and a resolver bean is specified.
     * The resolver must implement {@link BizScenarioResolver} and will be invoked
     * to determine the active {@code BizScenario}.
     *
     * @return the class of the custom resolver
     */
    Class<? extends BizScenarioResolver> customResolver() default BizScenarioResolver.class;
}
