package io.codeone.framework.api;

import java.lang.annotation.*;

/**
 * This annotation is used to mark an implementation of a service as API.
 * <p>
 * With this annotation on, the service implementation will be processed by the
 * following aspects:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface API {
}
