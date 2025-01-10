package io.codeone.framework.api.exception;

import lombok.Getter;

import java.util.Objects;

/**
 * Custom exception that implements {@link ApiError}.
 */
public class ApiException extends RuntimeException implements ApiError {

    @Getter
    private String code;

    @Getter
    private boolean critical;

    /**
     * Constructs a new {@code ApiException} with the specified code and severity.
     *
     * @param code     the error code
     * @param critical whether the error is critical
     */
    public ApiException(String code, boolean critical) {
        super();
        this.code = code;
        this.critical = critical;
    }

    /**
     * Constructs a new {@code ApiException} with the specified code, severity,
     * and message.
     *
     * @param code     the error code
     * @param critical whether the error is critical
     * @param message  the exception message
     */
    public ApiException(String code, boolean critical, String message) {
        super(message);
        this.code = code;
        this.critical = critical;
    }

    /**
     * Constructs a new {@code ApiException} with the specified code, severity,
     * and cause.
     *
     * @param code     the error code
     * @param critical whether the error is critical
     * @param cause    the cause of the exception
     */
    public ApiException(String code, boolean critical, Throwable cause) {
        super(cause);
        this.code = code;
        this.critical = critical;
    }

    /**
     * Constructs a new {@code ApiException} with the specified code, severity,
     * message, and cause.
     *
     * @param code     the error code
     * @param critical whether the error is critical
     * @param message  the exception message
     * @param cause    the cause of the exception
     */
    public ApiException(String code, boolean critical, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.critical = critical;
    }

    /**
     * Constructs a new {@code ApiException} using an {@link ApiError}.
     *
     * @param apiError the {@link ApiError} instance
     */
    public ApiException(ApiError apiError) {
        super(Objects.requireNonNull(apiError).getMessage());
        this.code = apiError.getCode();
        this.critical = apiError.isCritical();
    }

    /**
     * Constructs a new {@code ApiException} using an {@link ApiError} and message.
     *
     * @param apiError the {@link ApiError} instance
     * @param message  the exception message
     */
    public ApiException(ApiError apiError, String message) {
        super(message);
        Objects.requireNonNull(apiError);
        this.code = apiError.getCode();
        this.critical = apiError.isCritical();
    }

    /**
     * Constructs a new {@code ApiException} using an {@link ApiError} and cause.
     *
     * @param apiError the {@link ApiError} instance
     * @param cause    the cause of the exception
     */
    public ApiException(ApiError apiError, Throwable cause) {
        super(cause);
        Objects.requireNonNull(apiError);
        this.code = apiError.getCode();
        this.critical = apiError.isCritical();
    }

    /**
     * Constructs a new {@code ApiException} using an {@link ApiError}, message,
     * and cause.
     *
     * @param apiError the {@link ApiError} instance
     * @param message  the exception message
     * @param cause    the cause of the exception
     */
    public ApiException(ApiError apiError, String message, Throwable cause) {
        super(message, cause);
        Objects.requireNonNull(apiError);
        this.code = apiError.getCode();
        this.critical = apiError.isCritical();
    }
}