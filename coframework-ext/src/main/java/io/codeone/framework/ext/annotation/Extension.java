package io.codeone.framework.ext.annotation;

import io.codeone.framework.ext.util.BizScenarioUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Marks a class as an implementation of an {@link Extensible} interface.
 *
 * <p>Defines functionality dynamically routed based on a {@code BizScenario}. The
 * applicability of the extension is determined by its business identity ({@code
 * bizId}) and scenarios ({@code scenarios}).
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Component
public @interface Extension {

    /**
     * The business identity this extension targets.
     *
     * <p>Use {@code "*"} to apply to all business identities.
     *
     * @return the business identity
     */
    String bizId() default BizScenarioUtils.ANY;

    /**
     * The scenarios this extension targets.
     *
     * <p>Defines one or more scenarios for applicability. Use {@code "*"} to apply
     * to all scenarios.
     *
     * @return an array of scenarios
     */
    String[] scenarios() default {BizScenarioUtils.ANY};
}
