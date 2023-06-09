package io.codeone.framework.ext.session;

import java.lang.annotation.*;

/**
 * To resolve BizScenario and inject it into BizScenarioContext.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface ExtensionSession {

    BizScenarioResolvePolicy value() default BizScenarioResolvePolicy.AUTO;

    Class<? extends BizScenarioResolver> customResolver() default BizScenarioResolver.class;
}
