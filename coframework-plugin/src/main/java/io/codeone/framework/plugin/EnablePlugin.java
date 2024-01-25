package io.codeone.framework.plugin;

import io.codeone.framework.plugin.plugger.DefaultPlugger;

import java.lang.annotation.*;

/**
 * Use this annotation on classes/methods to specify what plugins will be
 * executed during the invocation of the target method.
 *
 * <p>This annotation is driven by {@link DefaultPlugger}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface EnablePlugin {

    /**
     * The classes of plugins that will be executed during the invocation of the
     * target method.
     *
     * @return the classes of plugins that will be executed during the
     * invocation of the target method
     */
    Class<? extends Plugin>[] value() default {};
}
