package io.codeone.framework.logging;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Logging {

    String name() default "";

    boolean logArgs() default true;

    boolean logResult() default true;

    boolean logException() default true;

    String expSuccess() default "";

    String expCode() default "";

    String expMessage() default "";

    String[] argKvs() default {};
}
