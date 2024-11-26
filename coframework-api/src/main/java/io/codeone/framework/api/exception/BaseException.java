package io.codeone.framework.api.exception;

import lombok.Getter;

/**
 * Base class for custom service exceptions.
 */
public abstract class BaseException extends RuntimeException implements ApiException {

    @Getter
    private String code;

    /**
     * Constructs an exception with the specified error code.
     *
     * @param code the error code
     */
    public BaseException(String code) {
        super();
        this.code = code;
    }

    /**
     * Constructs an exception with the specified error code and message.
     *
     * @param code    the error code
     * @param message the detail message
     */
    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Constructs an exception with the specified error code and cause.
     *
     * @param code  the error code
     * @param cause the cause
     */
    public BaseException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    /**
     * Constructs an exception with the specified error code, message, and cause.
     *
     * @param code    the error code
     * @param message the detail message
     * @param cause   the cause
     */
    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}