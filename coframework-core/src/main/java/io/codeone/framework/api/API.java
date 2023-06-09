package io.codeone.framework.api;

import java.lang.annotation.*;

/**
 * This annotation is used to mark an implementation of a service as API.
 * <p>
 * With this annotation on, the service implementation will be processed by the
 * following ApiPlugins:
 * <p>
 * 1. ArgCheckingApiPlugin, it will make sure any 'ApiParam' type of parameter
 * has its checkArgs() method passed before the execution of the service
 * method.
 * <p>
 * 2. LoggingApiPlugin, it will log the successfulness, code and message of an
 * API call.
 * <p>
 * 3. ExToResultApiPlugin, it will convert any exception to a failed Result and
 * return that result, if the return type of the service method is Result.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface API {

    /**
     * A more user-friendly message such as "System is busy, please try again
     * later." instead of verbose technical details returns to the customer
     * if there is an exception thrown.
     * <p>
     * This attribute does not affect the logging, and the original message
     * will always be logged.
     */
    String errorMessage() default "";
}
