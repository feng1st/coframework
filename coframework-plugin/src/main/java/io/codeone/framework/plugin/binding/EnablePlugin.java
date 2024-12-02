package io.codeone.framework.plugin.binding;

import io.codeone.framework.plugin.Plugin;

import java.lang.annotation.*;

/**
 * Enables one or more plugins for the annotated class or method.
 *
 * <p>This annotation can be applied at the class or method level to activate specific
 * plugins.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface EnablePlugin {

    /**
     * The plugins to be activated for the annotated element.
     *
     * @return an array of plugin classes
     */
    Class<? extends Plugin>[] value();
}
