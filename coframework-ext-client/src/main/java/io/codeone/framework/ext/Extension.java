package io.codeone.framework.ext;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Component
public @interface Extension {

    String[] bizId() default {BizScenario.ANY};

    String[] scenario() default {BizScenario.ANY};

    String[] scenarios() default {};
}
