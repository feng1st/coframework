package io.codeone.framework.exception;

/**
 * Base class of all business runtime exceptions.
 */
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

    @Override
    public String getCode() {
        return code;
    }

    public BaseException setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public BaseException setMessage(String message) {
        this.message = message;
        return this;
    }
}
