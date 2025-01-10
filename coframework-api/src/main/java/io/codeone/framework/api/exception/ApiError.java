package io.codeone.framework.api.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Represents an API error and its severity for service operations.
 *
 * <p>If a service or method is annotated with {@code API}, any exception implementing
 * {@code ApiError} or convertible to {@code ApiError} via {@code ApiErrorConverter}
 * will be transformed into a failed result with a specific error code and message
 * by the framework.
 *
 * <p>If the service or method is annotated with {@code API} or {@code Logging},
 * the framework will also recognize and log the error code, severity and message.
 */
public interface ApiError {

    /**
     * Creates a simple {@code ApiError} instance.
     *
     * @param code     the unique error code
     * @param critical whether the error is critical (logged as error) or not (logged
     *                 as warning)
     * @return a new {@code ApiError} instance
     */
    static ApiError of(String code, boolean critical, String message) {
        return new SimpleApiError(code, critical, message);
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
     * Returns the error message.
     *
     * @return the error message
     */
    String getMessage();

    /**
     * A simple implementation of {@link ApiError}.
     */
    @Data
    @RequiredArgsConstructor
    class SimpleApiError implements ApiError {

        /**
         * The error code.
         */
        private final String code;

        /**
         * Indicates if the error is critical.
         */
        private final boolean critical;

        /**
         * The error message.
         */
        private final String message;
    }
}
