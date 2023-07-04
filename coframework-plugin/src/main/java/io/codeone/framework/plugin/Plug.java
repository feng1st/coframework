package io.codeone.framework.plugin;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Applied on 'Plugin', to specify an order and the group it belongs.
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
     * The stage the plugin belongs to. Plugins will be sorted by stage in a
     * chain.
     * <p>
     * You can use the '@Order' annotation to further specify the order of
     * plugins that belong to the same stage, if that matters.
     *
     * @see Stages
     */
    Stages value() default Stages.BEFORE_TARGET;

    /**
     * The group the plugin belongs to. Use group to select multiple plugins at
     * once.
     * <p>
     * Empty value means the plugin does not belong to any group. In that case
     * you can select them only by specifying the individual classes of them.
     */
    String group() default "";
}
