package io.codeone.framework.plugin;

import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.GroupPlugging;
import io.codeone.framework.plugin.plug.MethodPlugger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Use this annotation on a plugin to specify an order and the group it belongs.
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
     * The stage this plugin belongs to. Plugins will be sorted by stage in a
     * chain. The default value, as well as the default stage value of a plugin
     * if you do not use the {@code Plug} annotation, is
     * {@link Stages#BEFORE_TARGET} which is closest to the target method in
     * order.
     *
     * <p>You can use {@link Order} annotation to further specify the order of
     * plugins that belong to the same stage, if that matters.
     *
     * @return the stage this plugin belongs to
     * @see Stages
     */
    Stages value() default Stages.BEFORE_TARGET;

    /**
     * The group this plugin belongs to. You can use group to select multiple
     * plugins at once. Empty value means that this plugin does not belong to
     * any group. In that case you can only select them by specifying the
     * individual classes of them.
     *
     * <p>You need to concern this attribute only if you want to write custom
     * pluggers, please refer to {@link GroupPlugging}, {@link ClassPlugging}
     * and {@link MethodPlugger} for more information.
     *
     * @return the group this plugin belongs to
     * @see ClassPlugging
     * @see GroupPlugging
     * @see MethodPlugger
     */
    String group() default "";
}
