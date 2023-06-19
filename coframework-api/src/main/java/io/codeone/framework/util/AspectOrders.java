package io.codeone.framework.util;

/**
 * Predefined constants for the order of aspects.
 */
public interface AspectOrders {

    /**
     * Wrapping an exception to a failed result, including those exceptions
     * thrown by arg-checking.
     */
    int WRAPPING_EXCEPTION = Integer.MAX_VALUE - 60;

    int BEFORE_WRAPPING_EXCEPTION = WRAPPING_EXCEPTION - 1;

    int AFTER_WRAPPING_EXCEPTION = WRAPPING_EXCEPTION + 1;

    /**
     * Logging the invocation of a method.
     */
    int LOGGING = Integer.MAX_VALUE - 40;

    int BEFORE_LOGGING = LOGGING - 1;

    int AFTER_LOGGING = LOGGING + 1;

    /**
     * Checking all args, may throw exceptions.
     */
    int CHECKING_ARGS = Integer.MAX_VALUE - 20;

    int BEFORE_CHECKING_ARGS = CHECKING_ARGS - 1;

    int AFTER_CHECKING_ARGS = CHECKING_ARGS + 1;
}
