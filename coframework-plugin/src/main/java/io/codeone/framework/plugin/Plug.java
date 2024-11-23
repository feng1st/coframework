package io.codeone.framework.plugin;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Component
public @interface Plug {

    Stages value();

    Class<? extends Annotation>[] targetAnnotations() default {};
}
