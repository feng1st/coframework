package io.codeone.framework.ext.annotation;

import java.lang.annotation.*;

/**
 * Meta-annotation to mark an interface as extensible.
 *
 * <p>This annotation serves as a foundational marker for extensible interfaces,
 * enabling implementations to be dynamically routed based on a {@code BizScenario}.
 *
 * <p>Direct usage of this annotation is discouraged. Instead, use {@link Ability}
 * or {@link ExtensionPoint} to define extensible interfaces.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Extensible {
}
