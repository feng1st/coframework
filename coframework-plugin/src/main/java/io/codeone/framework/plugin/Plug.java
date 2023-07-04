package io.codeone.framework.plugin;

import org.springframework.stereotype.Component;

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
@Component
public @interface Plug {

    /**
     * The stage the plugin belongs to.
     *
     * @see Stages
     */
    Stages value() default Stages.BEFORE_TARGET;
}
