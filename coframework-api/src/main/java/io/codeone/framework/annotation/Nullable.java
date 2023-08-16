package io.codeone.framework.annotation;

import java.lang.annotation.*;

/**
 * Use this annotation to declare that the return value of the annotated method
 * can be {@code null} under some circumstance.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Nullable {
}
