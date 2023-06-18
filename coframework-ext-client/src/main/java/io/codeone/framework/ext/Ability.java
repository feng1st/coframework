package io.codeone.framework.ext;

import java.lang.annotation.*;

/**
 * Ability is a business concept, an abstraction that indicates what tasks a
 * system can complete. The system can be designed to provide services with
 * abilities or Ability as a Service.
 * <p>
 * Ability annotation is used on interfaces, which represent the abilities
 * themselves. The methods of the interface can represent sub-abilities, steps
 * of the ability, extension points of the ability, etc.
 * <p>
 * An ability is an Extensible, please refer to the Extensible annotation for
 * more information.
 *
 * @see Extensible
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Extensible
public @interface Ability {

    /**
     * A human-readable name of the Ability.
     */
    String name() default "";

    /**
     * The description of the Ability.
     */
    String description() default "";

    /**
     * Optional annotation to add name, description and order to a method of an
     * ability.
     * <p>
     * All methods in an ability interface are extensible alongside the
     * interface, no matter have this annotation or not.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Method {

        /**
         * The name of the method.
         */
        String name() default "";

        /**
         * The description of the method.
         */
        String description() default "";

        /**
         * Logical order of this method, -1 means does not matter.
         */
        int order() default -1;
    }
}
