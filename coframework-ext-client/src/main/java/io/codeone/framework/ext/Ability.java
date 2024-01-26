package io.codeone.framework.ext;

import java.lang.annotation.*;

/**
 * Use this annotation to mark an Extensible interface which represents an
 * ability of the system. An ability is a concept that indicates what tasks a
 * system can complete, and in contrast to extension points (annotated by
 * {@link ExtensionPoint}), an ability is a higher level of abstraction. A
 * service can be an ability, or be made up of multiple abilities.
 *
 * <p>The methods of the ability interface can represent sub-abilities, steps of
 * the ability, extension points of the ability, etc.
 *
 * @see Extensible
 * @see ExtensionPoint
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Extensible
public @interface Ability {

    /**
     * A human-readable name of this ability.
     *
     * @return name of this ability
     */
    String name() default "";

    /**
     * The description of this ability.
     *
     * @return description of this ability
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

        /**
         * Virtual order of this Extensible method.
         *
         * @return order of this Extensible method
         */
        int order() default -1;
    }
}
