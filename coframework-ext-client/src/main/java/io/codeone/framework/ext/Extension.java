package io.codeone.framework.ext;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * This annotation is used to mark an Extension class, and set its "coordinate",
 * that is, for what business identity and scenario(s).
 *
 * <p>An Extension class is one of the implementations of an Extensible
 * interface. We autowire an Extensible interface, and route to a specific
 * Extension class by the {@link BizScenario} instance at runtime. Please refer
 * to {@link Extensible} for more details.
 *
 * @see Extensible
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Component
public @interface Extension {

    /**
     * Represents the business entity of this extension.
     *
     * <p>It can be set as a "." joined string e.g. "BIZ1.TEAM_A", or an array
     * of constants e.g. <code>{BIZ1, TEAM_A}</code>.
     *
     * @return the business entity of this extension
     */
    String[] bizId() default {BizScenario.ANY};

    /**
     * Represents the scenario of this extension.
     *
     * <p>It can be set as a "." joined string e.g. "holiday.new-year", or an
     * array of constants e.g. <code>{HOLIDAY, NEW_YEAR}</code>.
     *
     * @return the scenario of this extension
     */
    String[] scenario() default {BizScenario.ANY};

    /**
     * Represents multiple parallel scenarios of this extension. This attribute
     * has higher priority than the {@link #scenario()} if not empty.
     *
     * <p>It has to be set as an array of "." joined string, for example,
     * <code>{"holiday.new-year", "holiday.april-fools"}</code>.
     *
     * @return all parallel scenarios of this extension
     */
    String[] scenarios() default {};
}
