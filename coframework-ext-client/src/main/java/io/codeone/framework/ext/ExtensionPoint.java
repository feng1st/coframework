package io.codeone.framework.ext;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Extensible
public @interface ExtensionPoint {

    String name() default "";

    String description() default "";
}
