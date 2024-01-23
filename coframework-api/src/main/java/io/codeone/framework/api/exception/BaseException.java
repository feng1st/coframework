package io.codeone.framework.api.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Base class of all custom runtime exceptions. It implemented the
 * {@link ApiError} interface. You can construct one by specifying the code,
 * message and cause explicitly, or construct one by copying code and message
 * from an {@code ApiError}.
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseException extends RuntimeException
        implements ApiError {

    /**
     * The code to identify this exception. It should use a constant since this
     * is a part of the API.
     */
    private String code;

    /**
     * Constructs a new custom runtime exception from a code, a message and the
     * cause.
     *
     * @param code    the unique or semi-unique code of this exception
     * @param message the detail message which can be shown to end users
     * @param cause   the cause (A null value is permitted, and indicates that
     *                the cause is nonexistent or unknown.)
     */
    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Constructs a new custom runtime exception, witch itself is the root
     * cause.
     *
     * @param code    the unique or semi-unique code of this exception
     * @param message the detail message
     */
    public BaseException(String code, String message) {
        this(code, message, null);
    }

    /**
     * Constructs a new custom runtime exception from a code and the cause, and
     * uses the message of the cause as its message.
     *
     * @param code  the unique or semi-unique code of this exception
     * @param cause the cause, the message of which will be used as the message
     *              of this exception
     */
    public BaseException(String code, Throwable cause) {
        this(code, cause.getMessage(), cause);
    }

    /**
     * Constructs a new custom runtime exception from an {@code ApiError}, and
     * copies its code and message. This exception itself is the root cause.
     *
     * @param error the {@code ApiError} which is the source of the code and
     *              message
     */
    public BaseException(ApiError error) {
        this(error.getCode(), error.getMessage(), null);
    }

    /**
     * Constructs a new custom runtime exception from an {@code ApiError}, and
     * specifies its cause.
     *
     * @param error the {@code ApiError} which is the source of the code and
     *              message
     * @param cause that causes this exception
     */
    public BaseException(ApiError error, Throwable cause) {
        this(error.getCode(), error.getMessage(), cause);
    }

    /**
     * Constructs a new custom runtime exception from a {@code Throwable}. If
     * the throwable is also an {@link ApiError}, uses its code, otherwise uses
     * its simple class name as the code. And uses its message as the message
     * and the {@code Throwable} itself as the cause.
     *
     * @param cause the source of the code, message and the cause
     */
    public BaseException(Throwable cause) {
        this(cause instanceof ApiError ? ((ApiError) cause).getCode() : cause.getClass().getSimpleName(),
                cause.getMessage(), cause);
    }
}
