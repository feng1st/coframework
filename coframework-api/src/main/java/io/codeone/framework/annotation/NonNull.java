package io.codeone.framework.annotation;

import java.lang.annotation.*;

/**
 * Use the annotation to declare that the annotated parameter does not accept
 * {@code null} value.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Inherited
public @interface NonNull {
}
