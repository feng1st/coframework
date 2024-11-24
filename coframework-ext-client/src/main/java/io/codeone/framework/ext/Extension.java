package io.codeone.framework.ext;

import io.codeone.framework.ext.util.BizScenarioUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Component
public @interface Extension {

    String[] bizId() default {BizScenarioUtils.ANY};

    String[] scenario() default {BizScenarioUtils.ANY};

    String[] scenarios() default {};
}
