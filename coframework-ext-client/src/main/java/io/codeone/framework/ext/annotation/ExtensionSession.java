package io.codeone.framework.ext.annotation;

import io.codeone.framework.ext.BizScenarioResolver;
import io.codeone.framework.ext.constant.BizScenarioResolvePolicy;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface ExtensionSession {

    BizScenarioResolvePolicy value() default BizScenarioResolvePolicy.AUTO;

    Class<? extends BizScenarioResolver> customResolver() default BizScenarioResolver.class;
}
