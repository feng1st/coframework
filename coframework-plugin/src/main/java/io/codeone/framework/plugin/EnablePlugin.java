package io.codeone.framework.plugin;

import java.lang.annotation.*;

/**
 * Used on classes or methods to specify what plugins will be executed during
 * the invocation of the target.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface EnablePlugin {

    Class<? extends Plugin>[] value() default {};
}
