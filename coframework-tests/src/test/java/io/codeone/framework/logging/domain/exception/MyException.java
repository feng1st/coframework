package io.codeone.framework.logging.domain.exception;

import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.exception.BaseException;

public class MyException extends BaseException {

    public MyException(ApiError error) {
        super(error);
    }

    public MyException(ApiError error, Throwable cause) {
        super(error, cause);
    }
}
