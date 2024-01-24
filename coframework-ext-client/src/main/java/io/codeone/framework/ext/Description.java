package io.codeone.framework.ext;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Description {

    String name() default "";

    String description() default "";
}
