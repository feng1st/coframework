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
}
