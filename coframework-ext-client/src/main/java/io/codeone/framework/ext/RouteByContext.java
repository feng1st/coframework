package io.codeone.framework.ext;

import io.codeone.framework.ext.session.ExtensionSession;

import java.lang.annotation.*;

/**
 * Use this annotation on an Extensible interface or its methods to indicate
 * that the {@link BizScenario} instance in the context will be used to route an
 * Extension, instead of those in the parameters. To be specific, there is a
 * {@code BizScenario} stack in the context, and whenever an Extension routing
 * or an ({@link ExtensionSession}) resolving happens, the {@code BizScenario}
 * instance used/resolved will be pushed into that stack, and be popped
 * afterward.
 *
 * <p>You need to make sure there is a {@code BizScenario} instance in the
 * context (stack) if you are using this annotation, that is, only use it on
 * Extensible that will be executed either inside an {@code ExtensionSession} or
 * inside another Extensible invocation.
 *
 * <p>It is recommended to use {@code ExtensionSession} to get rid of individual
 * routing strategy control, please refer to {@code ExtensionSession} for more
 * information.
 *
 * @see Extensible
 * @see ExtensionSession
 * @see RouteBy
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RouteByContext {
}
