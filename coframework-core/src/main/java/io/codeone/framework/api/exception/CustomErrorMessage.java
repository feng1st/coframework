package io.codeone.framework.api.exception;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface CustomErrorMessage {

    String value();
}
