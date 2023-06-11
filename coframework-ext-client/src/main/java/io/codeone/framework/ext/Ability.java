package io.codeone.framework.ext;

import java.lang.annotation.*;

/**
 * Ability is an abstraction that indicates what tasks a system can complete. The system can be designed to provide
 * Services with Abilities or Ability as a Service.
 * <p>
 * Ability annotation is used on interfaces, which represent the abilities themselves. The methods of the interface can
 * represent sub-abilities, steps of the ability, extension points of the ability, etc.
 * <p>
 * Ability is Extensible, please refer to the Extensible annotation for more information.
 *
 * @see Extensible
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Extensible
public @interface Ability {

    /**
     * A human-readable name of this Ability.
     */
    String name() default "";

    /**
     * Description of this Ability.
     */
    String description() default "";
}
