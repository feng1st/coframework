package io.codeone.framework.ext;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * To mark an implementation of an extensible, and set its coordinate.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Component
public @interface Extension {

    /**
     * Represents the business entity of the extension.
     * <p>
     * It can be set as a "." joined string e.g. "BIZ_GLOBAL.TEAM_USER", or an
     * array of constants e.g. <code>{BIZ_GLOBAL, TEAM_USER}</code>.
     */
    String[] bizId() default {BizScenario.ANY};

    /**
     * Represents the scenario of the extension.
     * <p>
     * It can be set as a "." joined string e.g. "holiday.new-year", or an
     * array of constants e.g. <code>{HOLIDAY, NEW_YEAR}</code>.
     */
    String[] scenario() default {BizScenario.ANY};

    /**
     * Represents multiple parallel scenarios of the extension.
     * <p>
     * It has higher priority than the scenario attribute.
     * <p>
     * It has to be set as an array of "." joined string, for example,
     * <code>{"daily.weekend", "marketing.member-day"}</code>.
     */
    String[] scenarios() default {};
}
