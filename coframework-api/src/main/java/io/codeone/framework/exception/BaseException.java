package io.codeone.framework.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Base class of all custom runtime exceptions.
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseException extends RuntimeException
        implements ApiError {

    /**
     * The code to identify the exception. It should use a constant since this
     * is a part of the API.
     */
    private String code;

    /**
     * More specific details about the exception provided to customers.
     */
    private String message;

    public BaseException(String code, String message, Throwable cause) {
        super("[" + code + "]" + (message == null ? "" : message), cause);
        this.code = code;
        this.message = message;
    }

    public BaseException(String code, String message) {
        this(code, message, null);
    }

    public BaseException(String code, Throwable cause) {
        this(code, cause.getMessage(), cause);
    }

    public BaseException(ApiError error) {
        this(error.getCode(), error.getMessage(), null);
    }

    public BaseException(ApiError error, Throwable cause) {
        this(error.getCode(), error.getMessage(), cause);
    }

    public BaseException(Throwable cause) {
        this(cause.getClass().getSimpleName(), cause.getMessage(), cause);
    }
}
