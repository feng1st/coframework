package io.codeone.framework.api.exception;

import lombok.Getter;

import java.util.Objects;

/**
 * Custom exception that implements {@link ApiErrorCode}.
 */
public class ApiException extends RuntimeException implements ApiErrorCode {

    @Getter
    private String code;

    @Getter
    private boolean critical;

    /**
     * Constructs a new {@code ApiException} with the specified code and criticality.
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
     * Constructs a new {@code ApiException} with the specified code, criticality,
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
     * Constructs a new {@code ApiException} with the specified code, criticality,
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
     * Constructs a new {@code ApiException} with the specified code, criticality,
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
     * Constructs a new {@code ApiException} using an {@link ApiErrorCode}.
     *
     * @param apiErrorCode the {@link ApiErrorCode} instance
     */
    public ApiException(ApiErrorCode apiErrorCode) {
        super();
        Objects.requireNonNull(apiErrorCode);
        this.code = apiErrorCode.getCode();
        this.critical = apiErrorCode.isCritical();
    }

    /**
     * Constructs a new {@code ApiException} using an {@link ApiErrorCode} and message.
     *
     * @param apiErrorCode the {@link ApiErrorCode} instance
     * @param message      the exception message
     */
    public ApiException(ApiErrorCode apiErrorCode, String message) {
        super(message);
        Objects.requireNonNull(apiErrorCode);
        this.code = apiErrorCode.getCode();
        this.critical = apiErrorCode.isCritical();
    }

    /**
     * Constructs a new {@code ApiException} using an {@link ApiErrorCode} and cause.
     *
     * @param apiErrorCode the {@link ApiErrorCode} instance
     * @param cause        the cause of the exception
     */
    public ApiException(ApiErrorCode apiErrorCode, Throwable cause) {
        super(cause);
        Objects.requireNonNull(apiErrorCode);
        this.code = apiErrorCode.getCode();
        this.critical = apiErrorCode.isCritical();
    }

    /**
     * Constructs a new {@code ApiException} using an {@link ApiErrorCode}, message,
     * and cause.
     *
     * @param apiErrorCode the {@link ApiErrorCode} instance
     * @param message      the exception message
     * @param cause        the cause of the exception
     */
    public ApiException(ApiErrorCode apiErrorCode, String message, Throwable cause) {
        super(message, cause);
        Objects.requireNonNull(apiErrorCode);
        this.code = apiErrorCode.getCode();
        this.critical = apiErrorCode.isCritical();
    }
}