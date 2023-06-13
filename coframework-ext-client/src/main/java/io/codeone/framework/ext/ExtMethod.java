package io.codeone.framework.ext;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExtMethod {

    String name() default "";

    String description() default "";

    /**
     * Logical order of this method.
     */
    int order() default -1;
}
