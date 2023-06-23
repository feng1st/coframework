package io.codeone.framework.plugin;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Plug {

    Stage value() default Stage.BEFORE_TARGET;

    int order() default Integer.MAX_VALUE;
}
