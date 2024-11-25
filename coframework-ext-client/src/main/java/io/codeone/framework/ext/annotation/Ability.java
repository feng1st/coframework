package io.codeone.framework.ext.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Extensible
public @interface Ability {

    String name() default "";

    String description() default "";
}
