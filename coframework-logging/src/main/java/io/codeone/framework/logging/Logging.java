package io.codeone.framework.logging;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Logging {

    /**
     * To set how to trace the detail of the invocation.
     */
    LoggingPresets value() default LoggingPresets.NONE;

    /**
     * Set the name of the logger, default is the same to the annotated class.
     */
    String name() default "";

    /**
     * Set a list of name/SpEL expression pairs of 'key' information to be logged, e.g.
     * {"id", "#arg0?.id", "type", "#arg0?.type", ...}
     * <p>
     * Available variables: arguments "arg0", "arg1", ..., result "ret", and exception "err".
     * <p>
     * Set this attribute instead of LoggingPresets#logArgs()/logResult() to achieve a more compact output.
     */
    String[] keyPairs() default {};

    /**
     * Uses this SpEL expression to judge if the method had executed successfully.
     * <p>
     * For example, "#ret?.success" in which 'ret' is the variable name of the returned object.
     * <p>
     * The success of a method invocation is always false if there is an exception thrown.
     * <p>
     * This attribute will be ignored if the return type of the method is
     * {@link io.codeone.framework.response.Result}, Result#isSuccess() wil be used instead then.
     */
    String expSuccess() default "";

    /**
     * Uses this SpEL expression to retrieve the result 'code' of a method invocation.
     * <p>
     * For example, "#ret?.code" in which 'ret' is the variable name of the returned object.
     * <p>
     * If an exception has been thrown during the invocation, the 'code' will be generated from that exception.
     * ApiError#getCode() will be used if the thrown exception is an {@link io.codeone.framework.exception.ApiError},
     * or the 'code' will be "INVALID_PARAM" if the thrown exception is an IllegalArgumentException, otherwise it will
     * be the simple class name of that exception.
     * <p>
     * This attribute will be ignored if the return type of the method is
     * {@link io.codeone.framework.response.Result}, Result#getErrorCode() wil be used instead then.
     */
    String expCode() default "";

    /**
     * Uses this SpEL expression to retrieve the result 'message' of a method invocation.
     * <p>
     * For example, "#ret?.message" in which 'ret' is the variable name of the returned object.
     * <p>
     * The value will be Throwable#getMessage() if an exception has been thrown during the invocation.
     * <p>
     * This attribute will be ignored if the return type of the method is
     * {@link io.codeone.framework.response.Result}, Result#getErrorMessage() wil be used instead then.
     */
    String expMessage() default "";

    LogDelimiters delimiter() default LogDelimiters.DEFAULT;
}
