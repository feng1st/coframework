package io.codeone.framework.plugin;

import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.GroupPlugging;
import io.codeone.framework.plugin.plug.MethodPlugger;
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

    Stages value();

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
