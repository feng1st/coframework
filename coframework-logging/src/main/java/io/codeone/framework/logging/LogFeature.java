package io.codeone.framework.logging;

/**
 * Defines bitmask flags for controlling logging behavior in {@link Log}.
 *
 * <p>Constants represent toggleable logging features that can be combined using
 * bitwise OR operations. Used with {@link Log#config(long, boolean)} to manage
 * logging granularity.
 */
public interface LogFeature {

    /**
     * Enables automatic logging of all method arguments.
     *
     * <p>When active, captures all parameters using reflection-derived names.
     */
    long LOG_ALL_ARGS = 0x01;

    /**
     * Enables logging of method return values.
     *
     * <p>Excludes void methods by default.
     */
    long LOG_RESULT = 0x02;

    /**
     * Enables full stack trace logging for exceptions.
     *
     * <p>When disabled, only exception messages are logged.
     */
    long LOG_STACK_TRACE = 0x04;
}
