package io.codeone.framework.plugin;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Specifies a plugin for method interception, including the target stage and applicable
 * annotations.
 *
 * <p>This annotation should be applied to plugin classes to define their behavior
 * and scope.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Component
public @interface Plug {

    /**
     * The stage of method interception where the plugin will be applied.
     *
     * @return the target interception stage
     */
    Stages value();

    /**
     * Specifies the annotations that the plugin will target.
     *
     * <p>If not explicitly specified, the plugin can determine its targets through
     * {@code EnablePlugin} or {@code AnnoPluginBinding}.
     *
     * @return an array of annotation classes to target
     */
    Class<? extends Annotation>[] targetAnnotations() default {};
}
