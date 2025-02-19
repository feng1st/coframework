package io.codeone.framework.logging;

import java.lang.annotation.*;

/**
 * Annotation to configure logging on methods or classes.
 *
 * <p>Allows detailed logging of method arguments, results, exceptions, and custom expressions.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Logging {

    /**
     * Specifies the name of the logger to use.
     *
     * <p>Defaults to the name of the class where this annotation is applied.
     */
    String name() default "";

    /**
     * Enables logging of all method arguments by default.
     *
     * <p>Lowest precedence setting. Overridden if {@link #expArgKvs()} is specified.
     */
    boolean logAllArgs() default true;

    /**
     * Indicates whether to log the method result.
     *
     * <p>If set to {@code true}, logs the return value. If the return value is
     * compatible with {@code ApiResult}, the framework extracts and logs the data
     * within it.
     */
    boolean logResult() default true;

    /**
     * Determines whether the full stack trace of exceptions is logged.
     *
     * <p>When {@code true}, logs the complete stack trace for any thrown exception.
     */
    boolean logStackTrace() default true;

    /**
     * SpEL expression to determine if the method invocation was successful.
     *
     * <p>By default, the framework determines success based on the success of {@code
     * ApiResult} compatible results or the absence of exceptions.
     *
     * <p>Use {@code r} to reference the return value, e.g., {@code "#r?.success"}.
     */
    String expSuccess() default "";

    /**
     * SpEL expression to determine the error code.
     *
     * <p>By default, the framework extracts the error code from {@code ApiResult}
     * compatible results or {@code ApiError} compatible exceptions.
     *
     * <p>Use {@code r} to reference the return value, e.g., {@code "#r?.errorCode"}.
     */
    String expCode() default "";

    /**
     * SpEL expression to determine the error message.
     *
     * <p>By default, the framework extracts the error message from {@code ApiResult}
     * compatible results or exceptions.
     *
     * <p>Use {@code r} to reference the return value, e.g., {@code "#r?.errorMessage"}.
     */
    String expMessage() default "";

    /**
     * Key-value pairs for selective argument logging using SpEL expressions.
     *
     * <p>Format: {@code "key1", "expression1", "key2", "expression2", ...}. Overrides
     * {@link #logAllArgs()} when provided. Arguments are referenced via {@code
     * a0/p0, a1/p1} (parameters) or {@code r} (return value).
     */
    String[] expArgKvs() default {};
}
