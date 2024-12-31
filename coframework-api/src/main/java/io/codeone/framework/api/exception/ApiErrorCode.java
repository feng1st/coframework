package io.codeone.framework.api.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Represents an error code and its severity for service operations.
 *
 * <p>If a service or method is annotated with {@code API}, any exception implementing
 * {@code ApiErrorCode} or convertible to {@code ApiErrorCode} via {@code ApiErrorCodeConverter}
 * will be transformed into a failed result with a specific error code by the framework.
 *
 * <p>If the service or method is annotated with {@code API} or {@code Logging},
 * the framework will also recognize and log the error code and severity.
 */
public interface ApiErrorCode {

    /**
     * Creates a simple {@code ApiErrorCode} instance.
     *
     * @param code     the unique error code
     * @param critical whether the error is critical (logged as error) or not (logged
     *                 as warning)
     * @return a new {@code ApiErrorCode} instance
     */
    static ApiErrorCode of(String code, boolean critical) {
        return new SimpleApiErrorCode(code, critical);
    }

    /**
     * Retrieves the error code.
     *
     * @return the error code as a string
     */
    String getCode();

    /**
     * Determines if the error is critical.
     *
     * @return {@code true} if the error is critical and should be logged as an
     * error; {@code false} if it should be logged as a warning
     */
    boolean isCritical();

    /**
     * A simple implementation of {@link ApiErrorCode}.
     */
    @Data
    @RequiredArgsConstructor
    class SimpleApiErrorCode implements ApiErrorCode {

        /**
         * The error code.
         */
        private final String code;

        /**
         * Indicates if the error is critical.
         */
        private final boolean critical;
    }
}
