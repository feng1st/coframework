package io.codeone.framework.api.exception;

import java.lang.annotation.*;

/**
 * Annotation for providing custom error messages for service exceptions.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface CustomErrorMessage {

    /**
     * Returns the custom error message value.
     *
     * @return the custom error message value
     */
    String value();
}
