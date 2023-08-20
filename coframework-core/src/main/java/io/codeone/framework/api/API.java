package io.codeone.framework.api;

import io.codeone.framework.api.plugin.ArgCheckingApiPlugin;
import io.codeone.framework.api.plugin.ExToResultApiPlugin;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.aop.LoggingPlugin;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.request.ApiParam;
import io.codeone.framework.response.Result;

import java.lang.annotation.*;

/**
 * Marks an implementation of a service as an API.
 *
 * <p>With this annotation on, the service implementation will be intercepted by
 * the following {@link Plugin}s at runtime by default:
 *
 * <ol>
 * <li>{@link ArgCheckingApiPlugin} which will execute
 * {@link ApiParam#checkArgs()} on all arguments of the API call if the type of
 * those parameters is {@link ApiParam}.
 * <li>{@link LoggingPlugin} which by default will log the succesfulness, code
 * and message of an API call. You can use {@link Logging} annotation alongside
 * this annotation to further control the logging behavior.
 * <li>{@link ExToResultApiPlugin} which will convert any exception to a failed
 * {@link Result} and return that result, if the return type of the service
 * method is {@code Result}.
 * </ol>
 *
 * <p>And the service implementation will also be intercepted by any custom
 * plugin, which is:
 *
 * <ul>
 * <li>A subclass of {@code ApiPlugin}. Subclasses of {@code Plugin} are also
 * permitted but not recommended.
 * <li>Annotated by {@link Plug} with its {@code group} attribute set as
 * {@link ApiConstants#PLUGIN_GROUP}.
 * </ul>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface API {

    /**
     * Custom error message wrapped and returned to the end users. If the
     * default exception wrapping ({@link ExToResultApiPlugin}) happened, that
     * is, an exception had been thrown and the return type of the API method is
     * {@link Result}, instead of the original verbose technical error message,
     * the value of this attribute (if not empty) will be wrapped as the message
     * of the result, for example, "System is busy, please try again later".
     *
     * <p>This attribute does not affect the original exception and the logging
     * of that exception.
     */
    String errorMessage() default "";
}
