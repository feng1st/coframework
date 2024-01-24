package io.codeone.framework.ext;

import java.lang.annotation.*;

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
public @interface ExtMethod {

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
