package io.codeone.framework.exception;

/**
 * An out-of-box SysError type of exception.
 */
public class SysException extends BaseException implements SysError {

    public SysException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public SysException(String code, String message) {
        super(code, message);
    }

    public SysException(String code, Throwable cause) {
        super(code, cause);
    }

    public SysException(ApiError error) {
        super(error);
    }

    public SysException(ApiError error, Throwable cause) {
        super(error, cause);
    }

    public SysException(Throwable cause) {
        super(cause);
    }
}
