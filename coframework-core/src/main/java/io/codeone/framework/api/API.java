package io.codeone.framework.api;

import java.lang.annotation.*;

/**
 * Marks an implementation of a service as an API.
 *
 * <p>With this annotation on, the service implementation will be intercepted by
 * the following {@code Plugin}s at runtime by default:
 *
 * <ol>
 * <li>{@code ArgCheckingApiPlugin} which will execute
 * {@code ApiParam#checkArgs()} on all arguments of the API call if the type of
 * those parameters is {@code ApiParam}.
 * <li>{@code LoggingPlugin} which by default will log the succesfulness, code
 * and message of an API call. You can use {@code Logging} annotation alongside
 * this annotation to further control the logging behavior.
 * <li>{@code ExToResultApiPlugin} which will convert any exception to a failed
 * {@code Result} and return that result, if the return type of the service
 * method is {@code Result}.
 * </ol>
 *
 * <p>And the service implementation will also be intercepted by any custom
 * plugin, which is:
 *
 * <ul>
 * <li>A subclass of {@code ApiPlugin}. Subclasses of {@code Plugin} are also
 * permitted but not recommended.
 * <li>Annotated by {@code Plug} with its {@code group} attribute set as
 * {@code ApiConstants#PLUGIN_GROUP}.
 * </ul>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface API {
}
