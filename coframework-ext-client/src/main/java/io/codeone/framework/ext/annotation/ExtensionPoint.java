package io.codeone.framework.ext.annotation;

import java.lang.annotation.*;

/**
 * Indicates that an interface serves as an extension point for a specific domain.
 *
 * <p>An extension point represents a variation or specialization of rules, settings,
 * or logic for a particular domain or context. It is considered a lower-level concept
 * compared to {@link Ability}.
 *
 * <p>Interfaces annotated with this are a type of {@link Extensible} and can have
 * multiple {@code Extension} implementations tailored to various {@code BizScenario}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Extensible
public @interface ExtensionPoint {

    /**
     * The name of the extension point.
     *
     * @return the name of the extension point
     */
    String name() default "";

    /**
     * A brief description of the extension point.
     *
     * @return the description of the extension point
     */
    String description() default "";
}
