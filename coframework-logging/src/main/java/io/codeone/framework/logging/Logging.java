package io.codeone.framework.logging;

import java.lang.annotation.*;

/**
 * Annotation to configure logging on methods or classes.
 *
 * <p>Allows detailed logging of method arguments, results, exceptions, and custom
 * expressions.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Logging {

    /**
     * The name of the logger to use.
     *
     * <p>Defaults to the class name where this annotation is applied.
     */
    String name() default "";

    /**
     * Whether to log method arguments.
     *
     * <p>If {@code argKvs} is empty and this is set to {@code true}, all arguments
     * will be logged.
     */
    boolean logArgs() default true;

    /**
     * Whether to log the method result.
     *
     * <p>If set to {@code true}, logs the return value. If the return value is
     * compatible with {@code ApiResult}, the framework will extract and log the
     * data within it.
     */
    boolean logResult() default true;

    /**
     * Whether to log exceptions thrown by the method.
     *
     * <p>If {@code true}, logs the stack trace of thrown exceptions.
     */
    boolean logException() default true;

    /**
     * SpEL expression to determine if the invocation was successful.
     *
     * <p>Defaults to determining success based on {@code ApiResult} compatible
     * results or exceptions.
     *
     * <p>Use {@code r}, e.g., {@code "#r?.success"}.
     */
    String expSuccess() default "";

    /**
     * SpEL expression for determining the error code.
     *
     * <p>Defaults to extracting the code from {@code ApiResult} compatible results
     * or exceptions.
     *
     * <p>Use {@code r}, e.g., {@code "#r?.errorCode"}.
     */
    String expCode() default "";

    /**
     * SpEL expression for determining the error message.
     *
     * <p>Defaults to extracting the message from {@code ApiResult} compatible results
     * or exceptions.
     *
     * <p>Use {@code r}, e.g., {@code "#r?.errorMessage"}.
     */
    String expMessage() default "";

    /**
     * Key-value pairs for argument logging, using SpEL expressions.
     *
     * <p>Specify key-value pairs as {@code "name1", "expression1"}, etc.
     *
     * <p>Reference arguments using {@code a0}, {@code p0}, the result as {@code
     * r}, and exceptions as {@code e} or {@code t}, e.g., {@code "#a0?.userId"}.
     */
    String[] argKvs() default {};
}
