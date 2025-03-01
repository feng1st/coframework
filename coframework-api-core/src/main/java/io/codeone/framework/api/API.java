package io.codeone.framework.api;

import java.lang.annotation.*;

/**
 * Annotation for marking API methods or classes.
 *
 * <p>Due to the default plugins and annotation-plugin bindings in the framework,
 * this annotation:
 * <ul>
 *   <li>Validates {@code ApiParam} parameters of methods or services annotated
 *   with {@code API}.</li>
 *   <li>Transforms exceptions thrown by these methods into failed results, if applicable.</li>
 *   <li>Logs invocations of these methods or services and identifies {@code ApiResult}
 *   and {@code ApiError} compatible objects for enhanced logging.</li>
 * </ul>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface API {
}
