package io.codeone.framework.intercept;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Intercept {

    Stage value() default Stage.BEFORE_TARGET;

    int order() default Integer.MAX_VALUE;
}
