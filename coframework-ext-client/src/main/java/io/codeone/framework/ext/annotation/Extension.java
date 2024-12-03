package io.codeone.framework.ext.annotation;

import io.codeone.framework.ext.util.BizScenarioUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Marks a class as an implementation of an {@link Extensible} interface.
 *
 * <p>Classes annotated with this provide specific functionality dynamically routed
 * based on a {@code BizScenario}. These implementations define their scope using
 * business identity segments ({@code bizId}) and scenarios.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Component
public @interface Extension {

    /**
     * Defines the business identity that this extension applies to.
     *
     * <p>Using an array allows specification of hierarchical levels, such as {@code
     * {"region", "branch"}}.
     *
     * @return an array of business identity segments
     */
    String[] bizId() default {BizScenarioUtils.ANY};

    /**
     * Defines the specific scenario this extension applies to.
     *
     * <p>Using an array allows specification of hierarchical levels, such as {@code
     * {"weekday", "monday"}}. Specify either this or {@code scenarios}, but not
     * both.
     *
     * @return an array of scenario segments
     */
    String[] scenario() default {BizScenarioUtils.ANY};

    /**
     * Defines multiple specific scenarios this extension applies to.
     *
     * <p>For example: {@code {"weekday.monday", "weekday.friday"}}. Specify either
     * this or {@code scenario}, but not both.
     *
     * @return an array of specific scenarios
     */
    String[] scenarios() default {};
}
