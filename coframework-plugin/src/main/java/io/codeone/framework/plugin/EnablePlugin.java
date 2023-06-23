package io.codeone.framework.plugin;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface EnablePlugin {

    Class<? extends Plugin<?>>[] value() default {};
}
