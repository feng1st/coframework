package io.codeone.framework.plugin;

import java.lang.annotation.*;

/**
 * Applied on 'Plugin', to specify an order.
 * <p>
 * Plugins will be executed first by stage then by order.
 *
 * @see Plugin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Plug {

    /**
     * The stage the plugin belongs to.
     *
     * @see Stages
     */
    Stages value() default Stages.BEFORE_TARGET;

    /**
     * The order in that stage the plugin has.
     */
    int order() default Integer.MAX_VALUE;
}