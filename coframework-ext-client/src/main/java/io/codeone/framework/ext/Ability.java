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
}
