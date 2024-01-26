package io.codeone.framework.ext;

import java.lang.annotation.*;

/**
 * Use this annotation to mark an Extensible interface which represents an
 * extension point. An extension point could be a strategy, a rule, a
 * configuration, even a variable. Compared to {@link Ability}, an extension
 * point is a lower level of abstraction. Usually extension points are called by
 * abilities or services.
 *
 * @see Ability
 * @see Extensible
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Extensible
public @interface ExtensionPoint {

    /**
     * A human-readable name of this extension point.
     *
     * @return name of this extension point
     */
    String name() default "";

    /**
     * The description of this extension point.
     *
     * @return description of this extension point
     */
    String description() default "";

    /**
     * Adds name, description and order to a method of an Extensible. This
     * annotation is optional.
     *
     * <p>All methods in an Extensible interface are Extensible methods, no matter
     * having this annotation or not.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Method {

        /**
         * The name of this Extensible method.
         *
         * @return name of this Extensible method
         */
        String name() default "";

        /**
         * The description of this Extensible method.
         *
         * @return description of this Extensible method
         */
        String description() default "";
    }
}
