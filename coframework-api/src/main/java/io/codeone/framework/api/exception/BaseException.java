package io.codeone.framework.api.exception;

import lombok.Getter;

public abstract class BaseException extends RuntimeException implements ApiException {

    @Getter
    private String code;

    public BaseException(String code) {
        super();
        this.code = code;
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
