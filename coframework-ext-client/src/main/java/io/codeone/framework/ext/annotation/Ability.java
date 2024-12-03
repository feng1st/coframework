package io.codeone.framework.ext.annotation;

import java.lang.annotation.*;

/**
 * Indicates that an interface defines a specific ability or functionality.
 *
 * <p>An ability represents a high-level, generalized concept within the system,
 * and serves as a contract for behavior that can be extended or customized. It
 * is considered a higher-level concept compared to {@link ExtensionPoint}.
 *
 * <p>Interfaces annotated with this are a type of {@link Extensible} and can have
 * multiple {@link Extension} implementations tailored to different {@code BizScenario}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Extensible
public @interface Ability {

    /**
     * The name of the ability.
     *
     * @return the name of the ability
     */
    String name() default "";

    /**
     * A brief description of the ability.
     *
     * @return the description of the ability
     */
    String description() default "";
}
